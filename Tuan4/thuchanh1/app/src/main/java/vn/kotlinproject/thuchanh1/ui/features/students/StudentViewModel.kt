package vn.kotlinproject.thuchanh1.ui.feature.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vn.kotlinproject.thuchanh1.di.AppModule                // <- DI cũ (đổi nếu bạn chuyển package)
import vn.kotlinproject.thuchanh1.domain.repository.LibraryRepository

class StudentViewModel(private val repo: LibraryRepository) : ViewModel() {

    val students = repo.streamStudents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addStudent(name: String) = viewModelScope.launch {
        if (name.isNotBlank()) repo.addStudent(name.trim())
    }

    companion object {
        fun factory() = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return StudentViewModel(AppModule.repository) as T
            }
        }
    }
}
