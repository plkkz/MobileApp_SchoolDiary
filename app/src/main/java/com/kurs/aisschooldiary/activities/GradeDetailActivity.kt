package com.kurs.aisschooldiary.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.models.Grade
import com.kurs.aisschooldiary.viewmodel.GradeViewModel
import com.kurs.aisschooldiary.viewmodel.StudentViewModel
import com.kurs.aisschooldiary.viewmodel.ScheduleViewModel

class GradeDetailActivity : AppCompatActivity() {
    private val gradeViewModel: GradeViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private var gradeId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_detail)

        gradeId = intent.getLongExtra("GRADE_ID", 0)

        if (gradeId != 0L) {
            gradeViewModel.getGradeById(gradeId).observe(this) { grade ->
                grade?.let {
                    // Получаем полное имя студента
                    studentViewModel.getStudentById(it.studentId).observe(this) { student ->
                        student?.let {
                            val fullName = "${it.surname} ${it.name} ${it.patronym}"
                            findViewById<EditText>(R.id.edit_text_student_full_name).setText(fullName)
                        }
                    }

                    // Получаем название предмета
                    scheduleViewModel.getScheduleById(it.subjectId).observe(this) { subject ->
                        subject?.let {
                            findViewById<EditText>(R.id.edit_text_subject_name).setText(it.subjectName)
                        }
                    }

                    findViewById<EditText>(R.id.edit_text_grade_value).setText(it.grade.toString())
                    findViewById<EditText>(R.id.edit_text_date).setText(it.date)
                }
            }
        }

        findViewById<Button>(R.id.button_save).setOnClickListener {
            saveGrade()
        }

        findViewById<Button>(R.id.button_delete).setOnClickListener {
            deleteGrade()
        }
    }

    private fun saveGrade() {
        val studentFullName = findViewById<EditText>(R.id.edit_text_student_full_name).text.toString()
        val subjectName = findViewById<EditText>(R.id.edit_text_subject_name).text.toString()
        val gradeValue = findViewById<EditText>(R.id.edit_text_grade_value).text.toString().toInt()
        val date = findViewById<EditText>(R.id.edit_text_date).text.toString()

        // Здесь нужно будет получить studentId и subjectId на основе введенных данных
        // Это можно сделать через отдельные методы поиска в ваших ViewModel
        val studentId = studentViewModel.getIdByFullName(studentFullName) // Метод для получения ID по полному имени
        val subjectId = scheduleViewModel.getIdBySubjectName(subjectName) // Метод для получения ID по названию предмета

        val grade = Grade(gradeId, studentId, subjectId, gradeValue, date)
        if (gradeId == 0L) {
            gradeViewModel.insertGrade(grade)
        } else {
            gradeViewModel.updateGrade(grade)
        }
        finish()
    }

    private fun deleteGrade() {
        val grade = Grade(gradeId, 0, 0, 0, "")
        gradeViewModel.deleteGrade(grade)
        finish()
    }
}
