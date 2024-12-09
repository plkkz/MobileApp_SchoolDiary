package com.kurs.aisschooldiary.dao

import androidx.room.*
import com.kurs.aisschooldiary.models.Homework

@Dao
interface HomeworkDao {
    @Insert
    suspend fun insert(homework: Homework)

    @Update
    suspend fun update(homework: Homework)

    @Delete
    suspend fun delete(homework: Homework)

    @Query("SELECT * FROM homework")
    suspend fun getAllHomework(): List<Homework>

    @Query("SELECT * FROM homework WHERE homework_id = :id")
    suspend fun getHomeworkById(id: Int): Homework?
}