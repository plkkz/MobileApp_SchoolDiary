package com.kurs.aisschooldiary.models


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель домашнего задания, представляющая таблицу Homework в базе данных.
 */
@Entity
data class Homework(
    @PrimaryKey(autoGenerate = true) val homeworkId: Long = 0,
    val subjectId: Long,
    val description: String,
    val dueDate: String
)
