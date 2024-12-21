package com.kurs.aisschooldiary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.kurs.aisschooldiary.models.Homework

@Dao
interface HomeworkDao {

    @Insert
    suspend fun insertHomework(homework: Homework)

    @Update
    suspend fun updateHomework(homework: Homework)

    @Delete
    suspend fun deleteHomework(homework: Homework)

    @Query("SELECT * FROM Homework WHERE homeworkId = :id")
    suspend fun getHomeworkById(id: Long): Homework?

    @Query("SELECT * FROM Homework")
    suspend fun getAllHomeworks(): List<Homework>
}
