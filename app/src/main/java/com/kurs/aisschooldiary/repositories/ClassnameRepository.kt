package com.kurs.aisschooldiary.repositories


import com.kurs.aisschooldiary.dao.ClassnameDao
import com.kurs.aisschooldiary.models.Classname

class ClassnameRepository(private val classnameDao: ClassnameDao) {
    suspend fun insert(classname: Classname) {
        classnameDao.insert(classname)
    }

    suspend fun update(classname: Classname) {
        classnameDao.update(classname)
    }

    suspend fun delete(classname: Classname) {
        classnameDao.delete(classname)
    }

    suspend fun getClassnameById(id: Long): Classname? {
        return classnameDao.getClassnameById(id)
    }

    suspend fun getAllClassnames(): List<Classname> {
        return classnameDao.getAllClassnames()
    }
}

