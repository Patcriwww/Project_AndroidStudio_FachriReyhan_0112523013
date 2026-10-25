package com.example.a0112523013_fachrireyhan_p1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a0112523013_fachrireyhan_p1.data.Student
import com.example.a0112523013_fachrireyhan_p1.data.StudentSeed
import com.example.a0112523013_fachrireyhan_p1.validation.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Repository kecil berbasis memori, cukup untuk checkpoint navigasi. */
class StudentViewModel : ViewModel() {
    private val _students = MutableStateFlow(StudentSeed.create())
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    fun findById(id: Int): Student? = _students.value.firstOrNull { it.id == id }

    /**
     * Mengembalikan error kalau input tidak valid; null bila sukses.
     * ID otomatis untuk tambah, ID lama dipertahankan untuk edit.
     */
    fun save(
        editingId: Int?, name: String, nim: String, email: String,
        semester: Int, showEmail: Boolean
    ): String? {
        validateName(name)?.let { return it }
        validateNim(nim)?.let { return it }
        validateEmail(email)?.let { return it }
        validateSemester(semester)?.let { return it }
        val current = _students.value
        if (editingId != null && current.none { it.id == editingId }) {
            return "Data mahasiswa tidak ditemukan"
        }
        validateUniqueNim(nim, current.filter { it.id != editingId }.map { it.nim })
            ?.let { return it }

        val newId = editingId ?: (current.maxOfOrNull { it.id } ?: 0) + 1
        val saved = Student(
            id = newId,
            name = name.trim(),
            nim = nim,
            email = email.trim(),
            semester = semester,
            showEmail = showEmail
        )
        _students.update { old ->
            if (editingId == null) old + saved
            else old.map { if (it.id == editingId) saved else it }
        }
        return null
    }
}
