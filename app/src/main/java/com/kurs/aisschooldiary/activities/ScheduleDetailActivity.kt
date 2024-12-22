package com.kurs.aisschooldiary.ui

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.models.Classname // Предполагается, что у вас есть модель Classname
import com.kurs.aisschooldiary.models.Schedule
import com.kurs.aisschooldiary.viewmodel.ClassnameViewModel // Предполагается, что у вас есть ViewModel для Classname
import com.kurs.aisschooldiary.viewmodel.ScheduleViewModel

class ScheduleDetailActivity : AppCompatActivity() {
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private val classnameViewModel: ClassnameViewModel by viewModels() // ViewModel для получения предметов
    private var subjectId: Long = 0
    private lateinit var classnameSpinner: Spinner
    private lateinit var classnames: List<Classname>
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_detail)

        classnameSpinner = findViewById(R.id.spinner_classname)

        subjectId = intent.getLongExtra("SCHEDULE_ID", 0)

        // Инициализация GestureDetector
        gestureDetector = GestureDetector(this, GestureListener())

        // Загружаем предметы для Spinner
        classnameViewModel.getAllClassnames()
        classnameViewModel.classnames.observe(this) { classnames ->
            this.classnames = classnames
            setupSpinner(classnames)
        }

        if (subjectId != 0L) {
            scheduleViewModel.getScheduleById(subjectId).observe(this) { schedule ->
                schedule?.let {
                    findViewById<EditText>(R.id.edit_text_subject_name).setText(it.subjectName)
                    // Устанавливаем выбранный класс в Spinner
                    val position = classnames.indexOfFirst { classname -> classname.classnameId == it.classnameId }
                    classnameSpinner.setSelection(position)
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

    private fun setupSpinner(classnames: List<Classname>) {
        val classNamesList = classnames.map { it.className }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classNamesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classnameSpinner.adapter = adapter
    }

    private fun saveSchedule() {
        val subjectName = findViewById<EditText>(R.id.edit_text_subject_name).text.toString()
        val selectedClassName = classnameSpinner.selectedItem.toString()
        val selectedClass = classnames.find { it.className == selectedClassName }
        val classnameId = selectedClass?.classnameId ?: 0 // Получаем ID класса

        val schedule = Schedule(subjectId, subjectName, classnameId)
        if (subjectId == 0L) {
            scheduleViewModel.insertSchedule(schedule)
        } else {
            scheduleViewModel.updateSchedule(schedule)
        }
        finish()
    }

    private fun deleteSchedule() {
        if (subjectId != 0L) {
            scheduleViewModel.getScheduleById(subjectId).observe(this) { schedule ->
                schedule?.let {
                    scheduleViewModel.deleteSchedule(it)
                    finish()
                } ?: run {
                    Toast.makeText(this, "Ошибка: Расписание не найдено", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Ошибка: Расписание не выбрано для удаления", Toast.LENGTH_SHORT).show()
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
        // Переход на ScheduleListActivity
        finish() // Закрываем текущую Activity, чтобы вернуться на предыдущую
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Передаем события касания GestureDetector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}
