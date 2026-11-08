package com.example.a0112523013_fachrireyhan_p1.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY id ASC")
    fun observeAll(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): StudentEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(student: StudentEntity): Long

    @Update
    suspend fun update(student: StudentEntity): Int

    @Delete
    suspend fun delete(student: StudentEntity): Int
}
