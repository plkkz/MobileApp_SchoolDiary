package com.kurs.aisschooldiary

import android.app.Application

class SchoolApp : Application() {
    companion object {
        lateinit var database: SchoolDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = SchoolDatabase.getDatabase(this)
    }
}
