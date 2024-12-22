package com.kurs.aisschooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kurs.aisschooldiary.models.Student
import com.kurs.aisschooldiary.repositories.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> get() = _students

    // Создаем Job для управления корутинами
    private val job = Job()
    // Создаем собственный CoroutineScope
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun getAllStudents() {
        coroutineScope.launch {
            val studentList = repository.getAll()
            _students.postValue(studentList)
        }
    }

    fun getStudentById(id: Long): LiveData<Student?> {
        val studentLiveData = MutableLiveData<Student?>()
        coroutineScope.launch {
            val student = repository.getById(id)
            studentLiveData.postValue(student)
        }
        return studentLiveData
    }

    fun insertStudent(student: Student) {
        coroutineScope.launch {
            repository.insert(student)
            getAllStudents() // Обновляем список после вставки
        }
    }

    fun updateStudent(student: Student) {
        coroutineScope.launch {
            repository.update(student)
            getAllStudents() // Обновляем список после обновления
        }
    }

    fun deleteStudent(student: Student) {
        coroutineScope.launch {
            repository.delete(student)
            getAllStudents() // Обновляем список после удаления
        }
    }

    fun getIdByFullName(fullName: String): Long {
        var studentId: Long = 0
        coroutineScope.launch {
            val studentList = repository.getAll() // Получаем всех студентов
            studentList.find { "${it.surname} ${it.name} ${it.patronym}" == fullName }?.let {
                studentId = it.studentId
            }
        }
        return studentId // Возвращаем найденный studentId
    }

    // Отмена всех корутин при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        job.cancel() // Отмена всех активных корутин
    }
}
