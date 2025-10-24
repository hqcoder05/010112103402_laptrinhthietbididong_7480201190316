package vn.kotlinproject.thuchanh1.domain.model

data class Loan(
    val id: String,
    val studentId: String,
    val bookId: String,
    val borrowedAt: Long,      // thời điểm mượn (millis)
    val returnedAt: Long?      // null nếu chưa trả
)
