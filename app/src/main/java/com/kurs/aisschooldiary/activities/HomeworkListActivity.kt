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
import com.kurs.aisschooldiary.viewmodel.HomeworkViewModel

class HomeworkListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var homeworkAdapter: ArrayAdapter<String>
    private val homeworkViewModel: HomeworkViewModel by viewModels()
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework)

        listView = findViewById(R.id.list_view_homeworks)
        val homeworkDescriptions = mutableListOf<String>()

        // Инициализация адаптера для ListView
        homeworkAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, homeworkDescriptions)
        listView.adapter = homeworkAdapter

        findViewById<Button>(R.id.button_add_homework).setOnClickListener {
            openHomeworkDetail(0) // 0 для добавления нового домашнего задания
        }

        // Запрашиваем список домашних заданий из ViewModel
        homeworkViewModel.getAllHomeworks() // Запускаем загрузку домашних заданий
        homeworkViewModel.homeworks.observe(this) { homeworks ->
            // Обновляем список описаний домашних заданий
            homeworkDescriptions.clear()
            homeworkDescriptions.addAll(homeworks.map { it.description }) // Предполагается, что у домашнего задания есть поле description
            homeworkAdapter.notifyDataSetChanged()
        }

        // Обработка нажатия на элемент списка
        listView.setOnItemClickListener { _, _, position, _ ->
            val homeworkId = homeworkViewModel.homeworks.value?.get(position)?.homeworkId
            homeworkId?.let { openHomeworkDetail(it) }
        }

        // Инициализация GestureDetector
        gestureDetector = GestureDetector(this, GestureListener())
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

    private fun openHomeworkDetail(homeworkId: Long) {
        val intent = Intent(this, HomeworkDetailActivity::class.java)
        intent.putExtra("HOMEWORK_ID", homeworkId)
        startActivity(intent)
    }
}
