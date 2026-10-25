package com.example.a0112523013_fachrireyhan_p1.data

/** ID internal tidak sama dengan NIM: ID tetap stabil saat NIM diedit. */
data class Student(
    val id: Int,
    val name: String,
    val nim: String,
    val email: String,
    val semester: Int,
    val showEmail: Boolean
)

/** Data simulasi untuk demonstrasi: 8 item sesuai rubrik tugas. */
object StudentSeed {
    fun create(): List<Student> = listOf(
        Student(1, "Fachri Reyhan", "0112523013", "fachri@example.com", 7, true),
        Student(2, "Aditya Pratama", "0112523001", "aditya@example.com", 5, true),
        Student(3, "Nabila Putri", "0112523002", "nabila@example.com", 5, true),
        Student(4, "Rizky Maulana", "0112523003", "rizky@example.com", 7, false),
        Student(5, "Salsabila Zahra", "0112523004", "salsabila@example.com", 3, true),
        Student(6, "Dimas Ramadhan", "0112523005", "dimas@example.com", 7, true),
        Student(7, "Alya Maharani", "0112523006", "alya@example.com", 5, false),
        Student(8, "Farhan Akbar", "0112523007", "farhan@example.com", 3, true)
    )
}
