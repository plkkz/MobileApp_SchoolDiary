package com.kurs.aisschooldiary.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.models.Schedule
import com.kurs.aisschooldiary.ui.ScheduleDetailActivity
import com.kurs.aisschooldiary.viewmodel.ScheduleViewModel

class ScheduleListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var scheduleAdapter: ArrayAdapter<String>
    private val scheduleViewModel: ScheduleViewModel by viewModels()

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
    }

    private fun openScheduleDetail(subjectId: Long) {
        val intent = Intent(this, ScheduleDetailActivity::class.java)
        intent.putExtra("SCHEDULE_ID", subjectId)
        startActivity(intent)
    }
}
