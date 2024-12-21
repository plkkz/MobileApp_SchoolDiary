package com.kurs.aisschooldiary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.kurs.aisschooldiary.models.Student

/**
 * DAO для работы с таблицей Student.
 */
@Dao
interface StudentDao {
    @Insert
    suspend fun insert(student: Student)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("SELECT * FROM Student")
    suspend fun getAll(): List<Student>

    @Query("SELECT * FROM Student WHERE studentId = :id")
    suspend fun getById(id: Long): Student?
}


