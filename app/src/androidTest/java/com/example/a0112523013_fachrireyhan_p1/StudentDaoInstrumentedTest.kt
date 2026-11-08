package com.example.a0112523013_fachrireyhan_p1

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.a0112523013_fachrireyhan_p1.data.local.StudentDatabase
import com.example.a0112523013_fachrireyhan_p1.data.local.StudentEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/** Uji Room in-memory, berbeda dari uji manual persistence close/reopen. */
@RunWith(AndroidJUnit4::class)
class StudentDaoInstrumentedTest {
    @Test fun crudRoundTrip() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val db = Room.inMemoryDatabaseBuilder(context, StudentDatabase::class.java).build()
        try {
            val dao = db.studentDao()
            val id = dao.insert(StudentEntity(name="Uji Room",nim="0112523998",
                email="room@example.com",semester=5,showEmail=true)).toInt()
            assertEquals(1, dao.observeAll().first().size)
            val existing = requireNotNull(dao.getById(id))
            assertEquals("Uji Room", existing.name)
            assertEquals(1, dao.update(existing.copy(name="Room Diedit")))
            assertEquals("Room Diedit", dao.getById(id)?.name)
            assertEquals(1, dao.delete(requireNotNull(dao.getById(id))))
            assertTrue(dao.observeAll().first().isEmpty())
        } finally {
            db.close()
        }
    }
}
