package com.kurs.aisschooldiary.dao

import androidx.room.*
import com.kurs.aisschooldiary.models.Teacher
@Dao
interface TeacherDao {
    @Insert
    suspend fun insert(teacher: Teacher)

    @Update
    suspend fun update(teacher: Teacher)

    @Delete
    suspend fun delete(teacher: Teacher)

    @Query("SELECT * FROM teachers")
    suspend fun getAllTeachers(): List<Teacher>

    @Query("SELECT * FROM teachers WHERE teacher_id = :id")
    suspend fun getTeacherById(id: Int): Teacher?
}