package com.kurs.aisschooldiary.models

data class HomeworkGrade(
    val homeworkGradeId: Int, // Уникальный идентификатор оценки за домашнее задание (PK)
    val homeworkId: Int, // Идентификатор домашнего задания (FK)
    val studentId: Int, // Идентификатор ученика (FK)
    val grade: Int // Оценка за домашнее задание
)
