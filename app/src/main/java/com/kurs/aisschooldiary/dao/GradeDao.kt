package com.kurs.aisschooldiary.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.kurs.aisschooldiary.models.Grade

@Dao
interface GradeDao {

    @Insert
    suspend fun insert(grade: Grade)

    @Update
    suspend fun update(grade: Grade)

    @Delete
    suspend fun delete(grade: Grade)

    @Query("SELECT * FROM Grade WHERE studentId = :studentId ORDER BY date DESC")
    fun getGradesByStudentId(studentId: Long): LiveData<List<Grade>>

    @Query("SELECT * FROM Grade WHERE subjectId = :subjectId ORDER BY date DESC")
    fun getGradesBySubjectId(subjectId: Long): LiveData<List<Grade>>

    @Query("SELECT * FROM Grade ORDER BY date DESC")
    fun getAllGrades(): LiveData<List<Grade>>

    @Query("SELECT * FROM Grade WHERE gradeId = :gradeId")
    suspend fun getGradeById(gradeId: Long): Grade?
}
