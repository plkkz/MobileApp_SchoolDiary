package com.kurs.aisschooldiary.dao

import androidx.room.*
import com.kurs.aisschooldiary.models.Attendance

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insert(attendance: Attendance)

    @Update
    suspend fun update(attendance: Attendance)

    @Delete
    suspend fun delete(attendance: Attendance)

    @Query("SELECT * FROM attendance")
    suspend fun getAllAttendance(): List<Attendance>

    @Query("SELECT * FROM attendance WHERE attendance_id = :id")
    suspend fun getAttendanceById(id: Int): Attendance?
}