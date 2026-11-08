package com.example.a0112523013_fachrireyhan_p1.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.a0112523013_fachrireyhan_p1.data.Student

/** NIM unik di level SQLite juga, tidak hanya validasi pada UI. */
@Entity(tableName = "students", indices = [Index(value = ["nim"], unique = true)])
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val nim: String,
    val email: String,
    val semester: Int,
    val showEmail: Boolean
)

fun StudentEntity.toDomain() = Student(id, name, nim, email, semester, showEmail)
fun Student.toEntity() = StudentEntity(id, name, nim, email, semester, showEmail)
