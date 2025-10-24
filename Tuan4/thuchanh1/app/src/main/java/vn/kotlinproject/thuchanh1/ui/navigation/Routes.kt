package vn.kotlinproject.thuchanh1.ui.navigation

object Routes {
    const val Manage = "manage"
    const val Books = "books"
    const val Students = "students"

    private const val LoansBase = "loans"
    const val LoansPattern = "$LoansBase?studentId={studentId}"

    fun loans(studentId: String) = "$LoansBase?studentId=$studentId"
}
