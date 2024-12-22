package com.kurs.aisschooldiary.activities

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
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
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_detail)

        // Инициализация GestureDetector
        gestureDetector = GestureDetector(this, GestureListener())

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

    // Обработка жестов
    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (e1 == null) return false

            val SWIPE_THRESHOLD = 100
            val SWIPE_VELOCITY_THRESHOLD = 100

            val diffX = e2.x - e1.x
            val diffY = e2.y - e1.y

            // Проверяем, что свайп больше по горизонтали, чем по вертикали
            if (Math.abs(diffX) > Math.abs(diffY)) {
                // Проверяем, что свайп превышает порог и скорость
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX < 0) {
                        // Свайп влево
                        navigateBack()
                        return true
                    }
                }
            }
            return false
        }
    }

    private fun navigateBack() {
        // Закрываем текущую Activity, чтобы вернуться на GradeListActivity
        finish()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Передаем события касания GestureDetector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}
