package com.kurs.aisschooldiary.dao

import androidx.room.*
import com.kurs.aisschooldiary.models.SubjectTeach

@Dao
interface SubjectTeachDao {
    @Insert
    suspend fun insert(subject: SubjectTeach)

    @Update
    suspend fun update(subject: SubjectTeach)

    @Delete
    suspend fun delete(subject: SubjectTeach)

    @Query("SELECT * FROM subjects")
    suspend fun getAllSubjects(): List<SubjectTeach>

    @Query("SELECT * FROM subjects WHERE subject_id = :id")
    suspend fun getSubjectById(id: Int): SubjectTeach?
}