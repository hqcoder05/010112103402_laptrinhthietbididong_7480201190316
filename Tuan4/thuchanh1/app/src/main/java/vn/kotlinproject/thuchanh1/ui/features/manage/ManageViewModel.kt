package vn.kotlinproject.thuchanh1.ui.features.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vn.kotlinproject.thuchanh1.di.AppModule
import vn.kotlinproject.thuchanh1.domain.model.Book
import vn.kotlinproject.thuchanh1.domain.repository.LibraryRepository

data class BorrowedBookUI(val title: String, val checked: Boolean = true)
data class ManageUiState(
    val studentName: String = "",
    val books: List<BorrowedBookUI> = emptyList(),
    val message: String? = null
)

class ManageViewModel(private val repo: LibraryRepository) : ViewModel() {

    // --- sách còn trống để mượn ---
    val availableBooks: StateFlow<List<Book>> = repo.streamBooks()
        .map { list -> list.filter { it.isAvailable } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _ui = MutableStateFlow(ManageUiState())
    val ui: StateFlow<ManageUiState> = _ui

    private var listenJob: Job? = null
    private var currentStudentId: String? = null

    /** Bấm "Thay đổi" theo tên SV */
    fun changeStudentByName(inputName: String) {
        val name = inputName.trim()
        if (name.isBlank()) {
            _ui.value = ManageUiState(studentName = "", books = emptyList(), message = "Nhập tên sinh viên")
            currentStudentId = null
            listenJob?.cancel()
            return
        }

        listenJob?.cancel()
        _ui.value = _ui.value.copy(studentName = name, books = emptyList(), message = null)

        viewModelScope.launch {
            val student = repo.findStudentByName(name)
            if (student == null) {
                currentStudentId = null
                _ui.value = _ui.value.copy(books = emptyList(), message = "Không tìm thấy sinh viên")
                return@launch
            }
            currentStudentId = student.id

            listenJob = viewModelScope.launch {
                repo.streamLoansByStudent(student.id).collect { loans ->
                    val active = loans.filter { it.returnedAt == null }
                    val pills = active.map { l ->
                        val title = repo.getBookTitle(l.bookId) ?: l.bookId
                        BorrowedBookUI(title = title, checked = true)
                    }
                    _ui.value = _ui.value.copy(studentName = student.name, books = pills, message = null)
                }
            }
        }
    }

    /** Mượn một quyển từ sheet chọn sách */
    fun borrowSelected(bookId: String, onDone: () -> Unit) {
        val sId = currentStudentId ?: return
        viewModelScope.launch {
            repo.borrowBook(sId, bookId)
            onDone()
        }
    }

    companion object {
        fun factory(repo: LibraryRepository = AppModule.repository) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ManageViewModel(repo) as T
                }
            }
    }
}
