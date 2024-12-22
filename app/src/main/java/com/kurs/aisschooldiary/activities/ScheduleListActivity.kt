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
import com.kurs.aisschooldiary.ui.ScheduleDetailActivity
import com.kurs.aisschooldiary.viewmodel.ScheduleViewModel

class ScheduleListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var scheduleAdapter: ArrayAdapter<String>
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        listView = findViewById(R.id.list_view_schedules)
        val scheduleNames = mutableListOf<String>()

        // Инициализация адаптера для ListView
        scheduleAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, scheduleNames)
        listView.adapter = scheduleAdapter

        findViewById<Button>(R.id.button_add_schedule).setOnClickListener {
            openScheduleDetail(0) // 0 для добавления нового расписания
        }

        // Запрашиваем список расписаний из ViewModel
        scheduleViewModel.getAllSchedules() // Запускаем загрузку расписаний
        scheduleViewModel.schedules.observe(this) { schedules ->
            // Обновляем список имен расписаний
            scheduleNames.clear()
            scheduleNames.addAll(schedules.map { it.subjectName }) // Предполагается, что у расписания есть поле subjectName
            scheduleAdapter.notifyDataSetChanged()
        }

        // Обработка нажатия на элемент списка
        listView.setOnItemClickListener { _, _, position, _ ->
            val scheduleId = scheduleViewModel.schedules.value?.get(position)?.subjectId
            scheduleId?.let { openScheduleDetail(it) }
        }

        // Инициализация GestureDetector
        gestureDetector = GestureDetector(this, GestureListener())
    }

    private fun openScheduleDetail(subjectId: Long) {
        val intent = Intent(this, ScheduleDetailActivity::class.java)
        intent.putExtra("SCHEDULE_ID", subjectId)
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
                    if (diffX > 0) {
                        // Свайп вправо (можно добавить поведение, если нужно)
                    } else {
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
}
