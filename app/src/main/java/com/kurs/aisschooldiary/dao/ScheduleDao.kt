package com.kurs.aisschooldiary.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.kurs.aisschooldiary.models.Schedule

@Dao
interface ScheduleDao {

    @Insert
    suspend fun insert(schedule: Schedule)

    @Update
    suspend fun update(schedule: Schedule)

    @Delete
    suspend fun delete(schedule: Schedule)

    @Query("SELECT * FROM Schedule WHERE classnameId = :classnameId")
    suspend fun getSchedulesByClassnameId(classnameId: Long): List<Schedule>

    @Query("SELECT * FROM Schedule")
    suspend fun getAllSchedules(): List<Schedule>

    @Query("SELECT * FROM Schedule WHERE subjectId = :subjectId LIMIT 1")
    suspend fun getScheduleById(subjectId: Long): Schedule?
}
