package com.kurs.aisschooldiary.activities

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.MainActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.viewmodel.StudentViewModel

class StudentListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var studentAdapter: ArrayAdapter<String>
    private val studentViewModel: StudentViewModel by viewModels()
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        listView = findViewById(R.id.list_view_students)
        val studentNames = mutableListOf<String>()

        // Инициализация адаптера для ListView
        studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentNames)
        listView.adapter = studentAdapter

        findViewById<Button>(R.id.button_add_student).setOnClickListener {
            openStudentDetail(0) // 0 для добавления нового студента
        }

        // Запрашиваем список студентов из ViewModel
        studentViewModel.getAllStudents() // Запускаем загрузку студентов
        studentViewModel.students.observe(this) { students ->
            // Обновляем список имен студентов
            studentNames.clear()
            studentNames.addAll(students.map { "${it.surname} ${it.name} ${it.patronym}" })
            studentAdapter.notifyDataSetChanged()
        }

        // Обработка нажатия на элемент списка
        listView.setOnItemClickListener { _, _, position, _ ->
            val studentId = studentViewModel.students.value?.get(position)?.studentId
            studentId?.let { openStudentDetail(it) }
        }

        // Инициализация GestureDetector
        gestureDetector = GestureDetector(this, GestureListener())
        listView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
        }
    }

    private fun openStudentDetail(studentId: Long) {
        val intent = Intent(this, StudentDetailActivity::class.java)
        intent.putExtra("STUDENT_ID", studentId)
        startActivity(intent)
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
        // Переход на MainActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Закрываем текущую Activity
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Передаем события касания GestureDetector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}
