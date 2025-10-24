package vn.kotlinproject.thuchanh1.ui.features.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vn.kotlinproject.thuchanh1.di.AppModule
import vn.kotlinproject.thuchanh1.domain.repository.LibraryRepository

class BookViewModel(private val repo: LibraryRepository) : ViewModel() {
    val books = repo.streamBooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addBook(title: String) = viewModelScope.launch {
        if (title.isNotBlank()) repo.addBook(title.trim())
    }

    companion object {
        fun factory() = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BookViewModel(AppModule.repository) as T
            }
        }
    }
}
