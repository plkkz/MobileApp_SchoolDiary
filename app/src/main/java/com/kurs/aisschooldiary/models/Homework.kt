package com.kurs.aisschooldiary.models

data class Homework(
    val homeworkId: Int, // Уникальный идентификатор домашнего задания (PK)
    val subjectId: Int, // Идентификатор предмета (FK)
    val title: String, // Название домашнего задания
    val description: String, // Описание задания
    val dueDate: String // Дата сдачи задания
)
