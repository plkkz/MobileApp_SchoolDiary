package com.kurs.aisschooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kurs.aisschooldiary.models.Grade
import com.kurs.aisschooldiary.repositories.GradeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GradeViewModel(private val repository: GradeRepository) : ViewModel() {

    private val _grades = MutableLiveData<List<Grade>>()
    val grades: LiveData<List<Grade>> get() = _grades

    // Создаем Job для управления корутинами
    private val job = Job()
    // Создаем собственный CoroutineScope
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun getAllGrades() {
        coroutineScope.launch {
            val gradeList = repository.getAllGrades().value // Получаем список оценок
            _grades.postValue(gradeList)
        }
    }

    fun getGradesByStudentId(studentId: Long): LiveData<List<Grade>> {
        return repository.getGradesByStudentId(studentId)
    }

    fun getGradesBySubjectId(subjectId: Long): LiveData<List<Grade>> {
        return repository.getGradesBySubjectId(subjectId)
    }

    fun insertGrade(grade: Grade) {
        coroutineScope.launch {
            repository.insert(grade)
            getAllGrades() // Обновляем список после вставки
        }
    }

    fun updateGrade(grade: Grade) {
        coroutineScope.launch {
            repository.update(grade)
            getAllGrades() // Обновляем список после обновления
        }
    }

    fun deleteGrade(grade: Grade) {
        coroutineScope.launch {
            repository.delete(grade)
            getAllGrades() // Обновляем список после удаления
        }
    }

    // Получение оценки по идентификатору
    fun getGradeById(id: Long): LiveData<Grade?> {
        val gradeLiveData = MutableLiveData<Grade?>()
        coroutineScope.launch {
            val grade = repository.getGradeById(id)
            gradeLiveData.postValue(grade)
        }
        return gradeLiveData
    }

    // Отмена всех корутин при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        job.cancel() // Отмена всех активных корутин
    }
}
