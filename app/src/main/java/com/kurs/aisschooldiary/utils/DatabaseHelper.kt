package com.kurs.aisschooldiary.utils


import android.content.Context

/**
 * Утилита для работы с базой данных.
 */
object DatabaseHelper {
    lateinit var database: AppDatabase

    fun initializeDatabase(context: Context) {
        database = AppDatabase.getDatabase(context)
    }
}


