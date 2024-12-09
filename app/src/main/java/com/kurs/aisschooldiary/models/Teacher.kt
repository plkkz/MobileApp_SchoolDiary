package com.kurs.aisschooldiary.models

data class Teacher(
    val teacherId: Int, // Уникальный идентификатор учителя (PK)
    val surnameTeacher: String, // Фамилия учителя
    val nameTeacher: String, // Имя учителя
    val patronymTeacher: String, // Отчество учителя
    val subject: String // Предмет, который преподает учитель
)
