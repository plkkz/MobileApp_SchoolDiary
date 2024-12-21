package com.kurs.aisschooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kurs.aisschooldiary.models.Schedule
import com.kurs.aisschooldiary.repositories.ScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ScheduleViewModel(private val repository: ScheduleRepository) : ViewModel() {

    private val _schedules = MutableLiveData<List<Schedule>>()
    val schedules: LiveData<List<Schedule>> get() = _schedules

    // Создаем Job для управления корутинами
    private val job = Job()
    // Создаем собственный CoroutineScope
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun getAllSchedules() {
        coroutineScope.launch {
            val scheduleList = repository.getAllSchedules()
            _schedules.postValue(scheduleList)
        }
    }

    fun getSchedulesByClassnameId(classnameId: Long): LiveData<List<Schedule>> {
        val schedulesLiveData = MutableLiveData<List<Schedule>>()
        coroutineScope.launch {
            val scheduleList = repository.getSchedulesByClassnameId(classnameId)
            schedulesLiveData.postValue(scheduleList)
        }
        return schedulesLiveData
    }

    fun getScheduleById(subjectId: Long): LiveData<Schedule?> {
        val scheduleLiveData = MutableLiveData<Schedule?>()
        coroutineScope.launch {
            val schedule = repository.getScheduleById(subjectId)
            scheduleLiveData.postValue(schedule)
        }
        return scheduleLiveData
    }

    fun insertSchedule(schedule: Schedule) {
        coroutineScope.launch {
            repository.insert(schedule)
            getAllSchedules() // Обновляем список после вставки
        }
    }

    fun getIdBySubjectName(subjectName: String): Long {
        var subjectId: Long = 0
        coroutineScope.launch {
            val scheduleList = repository.getAllSchedules() // Получаем все расписания
            scheduleList.find { it.subjectName == subjectName }?.let {
                subjectId = it.subjectId
            }
        }
        return subjectId // Возвращаем найденный subjectId
    }


    fun updateSchedule(schedule: Schedule) {
        coroutineScope.launch {
            repository.update(schedule)
            getAllSchedules() // Обновляем список после обновления
        }
    }

    fun deleteSchedule(schedule: Schedule) {
        coroutineScope.launch {
            repository.delete(schedule)
            getAllSchedules() // Обновляем список после удаления
        }
    }

    // Отмена всех корутин при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        job.cancel() // Отмена всех активных корутин
    }
}
