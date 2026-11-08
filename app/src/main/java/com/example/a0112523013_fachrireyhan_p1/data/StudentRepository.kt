package com.example.a0112523013_fachrireyhan_p1.data

import com.example.a0112523013_fachrireyhan_p1.data.local.StudentDao
import com.example.a0112523013_fachrireyhan_p1.data.local.StudentEntity
import com.example.a0112523013_fachrireyhan_p1.data.local.toDomain
import com.example.a0112523013_fachrireyhan_p1.data.local.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Satu-satunya akses Room dari presentation layer adalah melalui repository. */
class StudentRepository(private val dao: StudentDao) {
    val students: Flow<List<Student>> = dao.observeAll().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun findById(id: Int): Student? = dao.getById(id)?.toDomain()

    suspend fun add(student: Student): Long =
        dao.insert(student.copy(id = 0).toEntity())

    suspend fun edit(student: Student): Boolean = dao.update(student.toEntity()) > 0

    suspend fun delete(id: Int): Boolean {
        val entity: StudentEntity = dao.getById(id) ?: return false
        return dao.delete(entity) > 0
    }
}
