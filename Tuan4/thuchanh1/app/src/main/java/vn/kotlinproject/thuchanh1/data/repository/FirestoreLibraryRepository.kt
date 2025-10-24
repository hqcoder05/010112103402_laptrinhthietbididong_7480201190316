package vn.kotlinproject.thuchanh1.data.repository

import kotlinx.coroutines.flow.Flow
import vn.kotlinproject.thuchanh1.data.remote.FirestoreDataSource
import vn.kotlinproject.thuchanh1.domain.model.*
import vn.kotlinproject.thuchanh1.domain.repository.LibraryRepository

class FirestoreLibraryRepository(
    private val remote: FirestoreDataSource
) : LibraryRepository {

    override fun streamBooks(): Flow<List<Book>> = remote.streamBooks()
    override suspend fun addBook(title: String) = remote.addBook(title)
    override suspend fun updateBookTitle(id: String, title: String) = remote.updateBookTitle(id, title)

    override fun streamStudents(): Flow<List<Student>> = remote.streamStudents()
    override suspend fun addStudent(name: String) = remote.addStudent(name)

    // ✅ tra cứu
    override suspend fun findStudentByName(name: String) = remote.findStudentByName(name)
    override suspend fun getBookTitle(bookId: String) = remote.getBookTitle(bookId)

    override fun streamLoansByStudent(studentId: String): Flow<List<Loan>> =
        remote.streamLoansByStudent(studentId)

    override suspend fun borrowBook(studentId: String, bookId: String) =
        remote.borrowBook(studentId, bookId)

    override suspend fun returnBook(loanId: String, bookId: String) =
        remote.returnBook(loanId, bookId)
}
