package com.kurs.aisschooldiary.dao

import androidx.room.*

import com.kurs.aisschooldiary.models.Student

@Dao
interface StudentDao {
    @Insert
    suspend fun insert(student: Student)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<Student>

    @Query("SELECT * FROM students WHERE student_id = :id")
    suspend fun getStudentById(id: Int): Student?
}