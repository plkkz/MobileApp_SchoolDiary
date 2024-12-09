package com.kurs.aisschooldiary.models

data class Student(
    val studentId: Int, // Уникальный идентификатор ученика (PK)
    val surnameStudent: String, // Фамилия ученика
    val nameStudent: String, // Имя ученика
    val patronymStudent: String, // Отчество ученика
    val birthDate: String, // Дата рождения
    val classId: Int // Идентификатор класса (FK)
)
