package vn.kotlinproject.thuchanh1.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import vn.kotlinproject.thuchanh1.domain.model.Book
import vn.kotlinproject.thuchanh1.domain.model.Loan
import vn.kotlinproject.thuchanh1.domain.model.Student
import java.util.Date

class FirestoreDataSource(
    private val db: FirebaseFirestore
) {

    // ---------- BOOKS ----------
    fun streamBooks(): Flow<List<Book>> = callbackFlow {
        val reg = db.collection("books")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, err ->
                if (err != null) { trySend(emptyList()); return@addSnapshotListener }
                val list = snap?.documents?.mapNotNull { d ->
                    val title = d.getString("title") ?: return@mapNotNull null
                    val isAvailable = d.getBoolean("isAvailable") ?: true
                    Book(id = d.id, title = title, isAvailable = isAvailable)
                } ?: emptyList()
                trySend(list)
            }
        awaitClose { reg.remove() }
    }

    suspend fun addBook(title: String) {
        val doc = hashMapOf(
            "title" to title,
            "isAvailable" to true,
            "createdAt" to Date()
        )
        db.collection("books").add(doc).await()
    }

    suspend fun updateBookTitle(id: String, title: String) {
        db.collection("books").document(id).update("title", title).await()
    }

    // ---------- STUDENTS ----------
    fun streamStudents(): Flow<List<Student>> = callbackFlow {
        val reg = db.collection("students")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, err ->
                if (err != null) { trySend(emptyList()); return@addSnapshotListener }
                val list = snap?.documents?.mapNotNull { d ->
                    val name = d.getString("name") ?: return@mapNotNull null
                    Student(id = d.id, name = name)
                } ?: emptyList()
                trySend(list)
            }
        awaitClose { reg.remove() }
    }

    suspend fun addStudent(name: String) {
        val doc = hashMapOf(
            "name" to name,
            "createdAt" to Date()
        )
        db.collection("students").add(doc).await()
    }

    // Tra SV theo tên (so khớp tuyệt đối)
    suspend fun findStudentByName(name: String): Student? {
        val snap = db.collection("students")
            .whereEqualTo("name", name)
            .limit(1)
            .get()
            .await()
        val d = snap.documents.firstOrNull() ?: return null
        val n = d.getString("name") ?: return null
        return Student(id = d.id, name = n)
    }

    // Lấy tiêu đề sách theo bookId
    suspend fun getBookTitle(bookId: String): String? {
        val doc = db.collection("books").document(bookId).get().await()
        return if (doc.exists()) doc.getString("title") else null
    }

    // ---------- LOANS ----------
    /**
     * Stream loans của 1 SV.
     * - Ưu tiên query có orderBy (cần composite index: studentId ASC, borrowedAt DESC).
     * - Nếu lỗi (thiếu index), fallback sang query không orderBy và sort tại client.
     */
    fun streamLoansByStudent(studentId: String): Flow<List<Loan>> = callbackFlow {
        var reg: ListenerRegistration? = null

        fun mapDocs(list: List<com.google.firebase.firestore.DocumentSnapshot>?): List<Loan> {
            return list?.mapNotNull { d ->
                val sId = d.getString("studentId") ?: return@mapNotNull null
                val bookId = d.getString("bookId") ?: return@mapNotNull null
                val borrowedAt = (d.getDate("borrowedAt") ?: d.getDate("createdAt"))?.time ?: 0L
                val returnedAt = d.getDate("returnedAt")?.time
                Loan(
                    id = d.id,
                    studentId = sId,
                    bookId = bookId,
                    borrowedAt = borrowedAt,
                    returnedAt = returnedAt
                )
            } ?: emptyList()
        }

        // Fallback: không orderBy, sort ở client
        fun attachFallbackListener() {
            reg = db.collection("loans")
                .whereEqualTo("studentId", studentId)
                .addSnapshotListener { snap, _ ->
                    val list = mapDocs(snap?.documents).sortedByDescending { it.borrowedAt }
                    trySend(list)
                }
        }

        // Preferred: có orderBy (cần index)
        fun attachOrderedListener() {
            reg = db.collection("loans")
                .whereEqualTo("studentId", studentId)
                .orderBy("borrowedAt", Query.Direction.DESCENDING)
                .addSnapshotListener { snap, err ->
                    if (err != null) {
                        reg?.remove()
                        attachFallbackListener()
                        return@addSnapshotListener
                    }
                    trySend(mapDocs(snap?.documents))
                }
        }

        attachOrderedListener()
        awaitClose { reg?.remove() }
    }

    suspend fun borrowBook(studentId: String, bookId: String) {
        val now = Date()
        val loan = hashMapOf(
            "studentId" to studentId,
            "bookId" to bookId,
            "borrowedAt" to now,
            "returnedAt" to null
        )
        db.collection("loans").add(loan).await()
        db.collection("books").document(bookId)
            .update("isAvailable", false)
            .await()
    }

    suspend fun returnBook(loanId: String, bookId: String) {
        db.collection("loans").document(loanId)
            .update("returnedAt", Date())
            .await()
        db.collection("books").document(bookId)
            .update("isAvailable", true)
            .await()
    }
}
