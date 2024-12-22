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
import com.kurs.aisschooldiary.viewmodel.GradeViewModel

class GradeListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var gradeAdapter: ArrayAdapter<String>
    private val gradeViewModel: GradeViewModel by viewModels()
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade)

        // Инициализация GestureDetector
        gestureDetector = GestureDetector(this, GestureListener())

        listView = findViewById(R.id.list_view_grades)
        val gradeDescriptions = mutableListOf<String>()

        // Инициализация адаптера для ListView
        gradeAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, gradeDescriptions)
        listView.adapter = gradeAdapter

        findViewById<Button>(R.id.button_add_grade).setOnClickListener {
            openGradeDetail(0) // 0 для добавления новой оценки
        }

        // Запрашиваем список оценок из ViewModel
        gradeViewModel.getAllGrades() // Запускаем загрузку оценок
        gradeViewModel.grades.observe(this) { grades ->
            // Обновляем список описаний оценок
            gradeDescriptions.clear()
            gradeDescriptions.addAll(grades.map { "Оценка: ${it.grade}, Дата: ${it.date}" }) // Используем поля grade и date
            gradeAdapter.notifyDataSetChanged()
        }

        // Обработка нажатия на элемент списка
        listView.setOnItemClickListener { _, _, position, _ ->
            val gradeId = gradeViewModel.grades.value?.get(position)?.gradeId
            gradeId?.let { openGradeDetail(it) }
        }
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
        startActivity(intent)
        finish() // Закрываем текущую Activity
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Передаем события касания GestureDetector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    private fun openGradeDetail(gradeId: Long) {
        val intent = Intent(this, GradeDetailActivity::class.java)
        intent.putExtra("GRADE_ID", gradeId)
        startActivity(intent)
    }
}
