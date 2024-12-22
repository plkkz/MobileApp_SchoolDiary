package com.kurs.aisschooldiary.repositories

import com.kurs.aisschooldiary.models.Student
import com.kurs.aisschooldiary.utils.AppDatabase

/**
 * Репозиторий для работы с учениками.
 * Этот класс управляет взаимодействием с базой данных.
 */
class StudentRepository private constructor(private val db: AppDatabase) {

    companion object {
        // Метод для создания экземпляра StudentRepository
        fun getInstance(db: AppDatabase): StudentRepository {
            return StudentRepository(db)
        }
    }

    /**
     * Вставляет нового студента в базу данных.
     * @param student - объект студента, который нужно вставить.
     */
    suspend fun insert(student: Student) {
        db.studentDao().insert(student)
    }

    /**
     * Обновляет информацию о существующем студенте в базе данных.
     * @param student - объект студента с обновленными данными.
     */
    suspend fun update(student: Student) {
        db.studentDao().update(student)
    }

    /**
     * Удаляет студента из базы данных.
     * @param student - объект студента, который нужно удалить.
     */
    suspend fun delete(student: Student) {
        db.studentDao().delete(student)
    }

    /**
     * Получает список всех студентов из базы данных.
     * @return список студентов.
     */
    suspend fun getAll(): List<Student> {
        return db.studentDao().getAll()
    }

    /**
     * Получает студента по его идентификатору.
     * @param id - идентификатор студента.
     * @return объект студента или null, если студент не найден.
     */
    suspend fun getById(id: Long): Student? {
        return db.studentDao().getById(id)
    }
}
