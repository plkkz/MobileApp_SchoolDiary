package com.kurs.aisschooldiary.models

data class Classnum(
    val classId: Int, // Уникальный идентификатор класса (PK)
    val className: String, // Название класса (например, "10А")
    val teacherId: Int // Идентификатор учителя, который ведет класс (FK)
)
