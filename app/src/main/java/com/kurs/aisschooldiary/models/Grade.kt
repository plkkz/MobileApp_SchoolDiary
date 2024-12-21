package com.kurs.aisschooldiary.models
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель оценки, представляющая таблицу Grade в базе данных.
 */
@Entity
data class Grade(
    @PrimaryKey(autoGenerate = true) val gradeId: Long = 0,
    val studentId: Long,
    val subjectId: Long,
    val grade: Int,
    val date: String
)
