package com.kurs.aisschooldiary.repositories

import com.kurs.aisschooldiary.models.Student
import com.kurs.aisschooldiary.utils.AppDatabase

/**
 * Репозиторий для работы с учениками.
 */
class StudentRepository(private val db: AppDatabase) {
    suspend fun insert(student: Student) {
        db.studentDao().insert(student)
    }

    suspend fun update(student: Student) {
        db.studentDao().update(student)
    }

    suspend fun delete(student: Student) {
        db.studentDao().delete(student)
    }

    suspend fun getAll(): List<Student> {
        return db.studentDao().getAll()
    }

    suspend fun getById(id: Long): Student? {
        return db.studentDao().getById(id)
    }
}
