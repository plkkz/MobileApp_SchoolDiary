package com.kurs.aisschooldiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kurs.aisschooldiary.models.Classname
import com.kurs.aisschooldiary.repositories.ClassnameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClassnameViewModel(private val repository: ClassnameRepository) : ViewModel() {

    private val _classnames = MutableLiveData<List<Classname>>()
    val classnames: LiveData<List<Classname>> get() = _classnames

    // Создаем Job для управления корутинами
    private val job = Job()
    // Создаем собственный CoroutineScope
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun getAllClassnames() {
        coroutineScope.launch {
            val classnameList = repository.getAllClassnames()
            _classnames.postValue(classnameList)
        }
    }

    fun getClassnameById(id: Long): LiveData<Classname?> {
        val classnameLiveData = MutableLiveData<Classname?>()
        coroutineScope.launch {
            val classname = repository.getClassnameById(id)
            classnameLiveData.postValue(classname)
        }
        return classnameLiveData
    }

    fun insert(classname: Classname) {
        coroutineScope.launch {
            repository.insert(classname)
            getAllClassnames() // Обновляем список после вставки
        }
    }

    fun update(classname: Classname) {
        coroutineScope.launch {
            repository.update(classname)
            getAllClassnames() // Обновляем список после обновления
        }
    }


    fun delete(classname: Classname) {
        coroutineScope.launch {
            repository.delete(classname)
            getAllClassnames() // Обновляем список после удаления
        }
    }

    // Отмена всех корутин при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        job.cancel() // Отмена всех активных корутин
    }
}
