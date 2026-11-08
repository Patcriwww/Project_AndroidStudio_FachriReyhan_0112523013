package com.example.a0112523013_fachrireyhan_p1.data

/** Model yang dipakai UI; ID Room stabil sekalipun NIM diubah. */
data class Student(
    val id: Int,
    val name: String,
    val nim: String,
    val email: String,
    val semester: Int,
    val showEmail: Boolean
)
