package com.kurs.aisschooldiary.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kurs.aisschooldiary.dao.*
import com.kurs.aisschooldiary.models.*

/**
 * Основной класс базы данных, который определяет таблицы и их связи.
 */
@Database(entities = [Classname::class, Student::class, Grade::class, Schedule::class, Homework::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun classDao(): ClassnameDao
    abstract fun studentDao(): StudentDao
    abstract fun gradeDao(): GradeDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun homeworkDao(): HomeworkDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "school_diary_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
