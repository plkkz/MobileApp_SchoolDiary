package com.kurs.aisschooldiary.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель класса, представляющая таблицу Classname в базе данных.
 */
@Entity
data class Classname(
    @PrimaryKey(autoGenerate = true) val classnameId: Long = 0,
    val className: String
)
