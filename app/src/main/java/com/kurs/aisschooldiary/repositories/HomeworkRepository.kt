package com.kurs.aisschooldiary.repositories

import com.kurs.aisschooldiary.dao.HomeworkDao
import com.kurs.aisschooldiary.models.Homework

class HomeworkRepository(private val homeworkDao: HomeworkDao) {

    suspend fun insert(homework: Homework) {
        homeworkDao.insertHomework(homework)
    }

    suspend fun update(homework: Homework) {
        homeworkDao.updateHomework(homework)
    }

    suspend fun delete(homework: Homework) {
        homeworkDao.deleteHomework(homework)
    }

    suspend fun getHomeworkById(id: Long): Homework? {
        return homeworkDao.getHomeworkById(id)
    }

    suspend fun getAllHomeworks(): List<Homework> {
        return homeworkDao.getAllHomeworks()
    }
}
