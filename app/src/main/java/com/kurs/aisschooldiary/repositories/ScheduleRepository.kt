package com.kurs.aisschooldiary.repositories

import com.kurs.aisschooldiary.dao.ScheduleDao
import com.kurs.aisschooldiary.models.Schedule

class ScheduleRepository(private val scheduleDao: ScheduleDao) {

    suspend fun insert(schedule: Schedule) {
        scheduleDao.insert(schedule)
    }

    suspend fun update(schedule: Schedule) {
        scheduleDao.update(schedule)
    }

    suspend fun delete(schedule: Schedule) {
        scheduleDao.delete(schedule)
    }

    suspend fun getSchedulesByClassnameId(classnameId: Long): List<Schedule> {
        return scheduleDao.getSchedulesByClassnameId(classnameId)
    }

    suspend fun getAllSchedules(): List<Schedule> {
        return scheduleDao.getAllSchedules()
    }

    suspend fun getScheduleById(subjectId: Long): Schedule? {
        return scheduleDao.getScheduleById(subjectId)
    }
}
