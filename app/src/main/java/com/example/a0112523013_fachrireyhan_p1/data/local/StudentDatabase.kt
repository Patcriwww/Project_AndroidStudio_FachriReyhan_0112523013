package com.example.a0112523013_fachrireyhan_p1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [StudentEntity::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile private var INSTANCE: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                StudentDatabase::class.java,
                "student_profile_manager.db"
            ).addCallback(object : Callback() {
                /** Hanya jalan saat database dibuat PERTAMA KALI, bukan setiap app start. */
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL(
                        """INSERT INTO students (id,name,nim,email,semester,showEmail) VALUES
                            (1,'Fachri Reyhan','0112523013','fachri@example.com',7,1),
                            (2,'Aditya Pratama','0112523001','aditya@example.com',5,1),
                            (3,'Nabila Putri','0112523002','nabila@example.com',5,1),
                            (4,'Rizky Maulana','0112523003','rizky@example.com',7,0),
                            (5,'Salsabila Zahra','0112523004','salsabila@example.com',3,1),
                            (6,'Dimas Ramadhan','0112523005','dimas@example.com',7,1),
                            (7,'Alya Maharani','0112523006','alya@example.com',5,0),
                            (8,'Farhan Akbar','0112523007','farhan@example.com',3,1)""".trimIndent()
                    )
                }
            }).build().also { INSTANCE = it }
        }
    }
}
