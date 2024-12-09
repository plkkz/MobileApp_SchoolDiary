package com.kurs.aisschooldiary.dao

import androidx.room.*
import com.kurs.aisschooldiary.models.Grade

@Dao
interface GradeDao {
    @Insert
    suspend fun insert(grade: Grade)

    @Update
    suspend fun update(grade: Grade)

    @Delete
    suspend fun delete(grade: Grade)

    @Query("SELECT * FROM grades")
    suspend fun getAllGrades(): List<Grade>

    @Query("SELECT * FROM grades WHERE grade_id = :id")
    suspend fun getGradeById(id: Int): Grade?
}