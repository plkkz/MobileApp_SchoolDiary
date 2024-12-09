package com.kurs.aisschooldiary.models

data class Grade(
    val gradeId: Int, // Уникальный идентификатор оценки (PK)
    val studentId: Int, // Идентификатор ученика (FK)
    val subjectId: Int, // Идентификатор предмета (FK)
    val grade: Int, // Оценка (например, 5, 4, 3 и т.д.)
    val date: String // Дата выставления оценки
)
