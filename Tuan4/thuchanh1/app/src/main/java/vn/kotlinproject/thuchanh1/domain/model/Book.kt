package vn.kotlinproject.thuchanh1.domain.model

data class Book(
    val id: String,
    val title: String,
    val isAvailable: Boolean = true
)