package com.kurs.aisschooldiary.models

data class Attendance(
    val attendanceId: Int, // Уникальный идентификатор посещения (PK)
    val studentId: Int, // Идентификатор ученика (FK)
    val date: String, // Дата посещения
    val status: String // Статус (например, "Присутствует", "Отсутствует", "Опоздал")
)
