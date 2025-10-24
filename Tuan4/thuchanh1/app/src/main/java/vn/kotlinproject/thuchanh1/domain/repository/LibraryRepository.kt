package vn.kotlinproject.thuchanh1.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.kotlinproject.thuchanh1.domain.model.*

interface LibraryRepository {
    // Books
    fun streamBooks(): Flow<List<Book>>
    suspend fun addBook(title: String)
    suspend fun updateBookTitle(id: String, title: String)

    // Students
    fun streamStudents(): Flow<List<Student>>
    suspend fun addStudent(name: String)

    // ✅ tra cứu
    suspend fun findStudentByName(name: String): Student?
    suspend fun getBookTitle(bookId: String): String?

    // Loans
    fun streamLoansByStudent(studentId: String): Flow<List<Loan>>
    suspend fun borrowBook(studentId: String, bookId: String)
    suspend fun returnBook(loanId: String, bookId: String)
}
