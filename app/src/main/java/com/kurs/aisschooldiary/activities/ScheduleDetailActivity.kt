package com.kurs.aisschooldiary.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.models.Schedule
import com.kurs.aisschooldiary.viewmodel.ScheduleViewModel

class ScheduleDetailActivity : AppCompatActivity() {
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private var subjectId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_detail)

        subjectId = intent.getLongExtra("SCHEDULE_ID", 0)

        if (subjectId != 0L) {
            scheduleViewModel.getScheduleById(subjectId).observe(this) { schedule ->
                schedule?.let {
                    findViewById<EditText>(R.id.edit_text_subject_name).setText(it.subjectName)
                    // Здесь можно добавить другие поля, если они есть в модели Schedule
                }
            }
        }

        findViewById<Button>(R.id.button_save).setOnClickListener {
            saveSchedule()
        }

        findViewById<Button>(R.id.button_delete).setOnClickListener {
            deleteSchedule()
        }
    }

    private fun saveSchedule() {
        val subjectName = findViewById<EditText>(R.id.edit_text_subject_name).text.toString()
        // Здесь можно добавить другие поля, если они есть в модели Schedule

        val schedule = Schedule(subjectId, subjectName, 0) // Замените 0 на нужный classnameId
        if (subjectId == 0L) {
            scheduleViewModel.insertSchedule(schedule)
        } else {
            scheduleViewModel.updateSchedule(schedule)
        }
        finish()
    }

    private fun deleteSchedule() {
        val schedule = Schedule(subjectId, "", 0) // Замените 0 на нужный classnameId
        scheduleViewModel.deleteSchedule(schedule)
        finish()
    }
}
