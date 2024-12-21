package com.kurs.aisschooldiary.models


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель расписания, представляющая таблицу Schedule в базе данных.
 */
@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true) val subjectId: Long = 0,
    val subjectName: String,
    val classnameId: Long
)
