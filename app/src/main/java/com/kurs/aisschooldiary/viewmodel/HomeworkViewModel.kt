package com.kurs.aisschooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kurs.aisschooldiary.models.Homework
import com.kurs.aisschooldiary.repository.HomeworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeworkViewModel(private val repository: HomeworkRepository) : ViewModel() {

    private val _homeworks = MutableLiveData<List<Homework>>()
    val homeworks: LiveData<List<Homework>> get() = _homeworks

    // Создаем Job для управления корутинами
    private val job = Job()
    // Создаем собственный CoroutineScope
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun getAllHomeworks() {
        coroutineScope.launch {
            val homeworkList = repository.getAllHomeworks()
            _homeworks.postValue(homeworkList)
        }
    }

    fun getHomeworkById(id: Long): LiveData<Homework?> {
        val homeworkLiveData = MutableLiveData<Homework?>()
        coroutineScope.launch {
            val homework = repository.getHomeworkById(id)
            homeworkLiveData.postValue(homework)
        }
        return homeworkLiveData
    }

    fun insertHomework(homework: Homework) {
        coroutineScope.launch {
            repository.insert(homework)
            getAllHomeworks() // Обновляем список после вставки
        }
    }

    fun updateHomework(homework: Homework) {
        coroutineScope.launch {
            repository.update(homework)
            getAllHomeworks() // Обновляем список после обновления
        }
    }

    fun deleteHomework(homework: Homework) {
        coroutineScope.launch {
            repository.delete(homework)
            getAllHomeworks() // Обновляем список после удаления
        }
    }

    // Отмена всех корутин при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        job.cancel() // Отмена всех активных корутин
    }
}
