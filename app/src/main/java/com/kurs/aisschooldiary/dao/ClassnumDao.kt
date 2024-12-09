package com.kurs.aisschooldiary.dao

import androidx.room.*
import com.kurs.aisschooldiary.models.Classnum

@Dao
interface ClassnumDao {
    @Insert
    suspend fun insert(classEntity: Classnum)

    @Update
    suspend fun update(classEntity: Classnum)

    @Delete
    suspend fun delete(classEntity: Classnum)

    @Query("SELECT * FROM classes")
    suspend fun getAllClasses(): List<Classnum>

    @Query("SELECT * FROM classes WHERE class_id = :id")
    suspend fun getClassById(id: Int): Classnum?
}