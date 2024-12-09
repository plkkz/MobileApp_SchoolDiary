package com.kurs.aisschooldiary.dao

import androidx.room.*
import com.kurs.aisschooldiary.models.HomeworkGrade

@Dao
interface HomeworkGradeDao {
    @Insert
    suspend fun insert(homeworkGrade: HomeworkGrade)

    @Update
    suspend fun update(homeworkGrade: HomeworkGrade)

    @Delete
    suspend fun delete(homeworkGrade: HomeworkGrade)

    @Query("SELECT * FROM homework_grades")
    suspend fun getAllHomeworkGrades(): List<HomeworkGrade>

    @Query("SELECT * FROM homework_grades WHERE homework_grade_id = :id")
    suspend fun getHomeworkGradeById(id: Int): HomeworkGrade?
}