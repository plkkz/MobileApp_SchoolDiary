package com.kurs.aisschooldiary.repositories

import androidx.lifecycle.LiveData
import com.kurs.aisschooldiary.dao.GradeDao
import com.kurs.aisschooldiary.models.Grade

class GradeRepository(private val gradeDao: GradeDao) {

    fun getGradesByStudentId(studentId: Long): LiveData<List<Grade>> {
        return gradeDao.getGradesByStudentId(studentId)
    }

    fun getGradesBySubjectId(subjectId: Long): LiveData<List<Grade>> {
        return gradeDao.getGradesBySubjectId(subjectId)
    }

    fun getAllGrades(): LiveData<List<Grade>> {
        return gradeDao.getAllGrades()
    }

    suspend fun getGradeById(gradeId: Long): Grade? {
        return gradeDao.getGradeById(gradeId)
    }

    suspend fun insert(grade: Grade) {
        gradeDao.insert(grade)
    }

    suspend fun update(grade: Grade) {
        gradeDao.update(grade)
    }

    suspend fun delete(grade: Grade) {
        gradeDao.delete(grade)
    }
}
