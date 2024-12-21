package com.kurs.aisschooldiary.models


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель ученика, представляющая таблицу Student в базе данных.
 */
@Entity
data class Student(
    @PrimaryKey(autoGenerate = true) val studentId: Long = 0,
    val surname: String,
    val name: String,
    val patronym: String,
    val phoneNumber: String,
    val classnameId: Long
)

