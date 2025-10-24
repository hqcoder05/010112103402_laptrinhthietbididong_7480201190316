package vn.kotlinproject.thuchanh1.ui.features.loans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import vn.kotlinproject.thuchanh1.di.AppModule                 // <- DI cũ (đổi nếu bạn chuyển package)
import vn.kotlinproject.thuchanh1.domain.repository.LibraryRepository

class LoanViewModel(private val repo: LibraryRepository) : ViewModel() {

    private val _studentId = MutableStateFlow("")
    val studentId: StateFlow<String> = _studentId

    val loans = _studentId
        .flatMapLatest { id -> if (id.isBlank()) flowOf(emptyList()) else repo.streamLoansByStudent(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun setStudent(id: String) { _studentId.value = id }

    fun borrow(bookId: String) = viewModelScope.launch {
        val sId = _studentId.value
        if (sId.isNotBlank() && bookId.isNotBlank()) repo.borrowBook(sId, bookId.trim())
    }

    fun returnLoan(loanId: String, bookId: String) = viewModelScope.launch {
        if (loanId.isNotBlank() && bookId.isNotBlank()) repo.returnBook(loanId, bookId)
    }

    companion object {
        fun factory() = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoanViewModel(AppModule.repository) as T
            }
        }
    }
}
