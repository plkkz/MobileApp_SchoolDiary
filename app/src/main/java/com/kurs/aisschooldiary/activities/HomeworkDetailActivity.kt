package com.kurs.aisschooldiary.activities

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.models.Homework
import com.kurs.aisschooldiary.viewmodel.HomeworkViewModel

class HomeworkDetailActivity : AppCompatActivity() {
    private val homeworkViewModel: HomeworkViewModel by viewModels()
    private var homeworkId: Long = 0
    private var subjectId: Long = 0 // Добавляем переменную для subjectId
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework_detail)

        // Инициализация GestureDetector
        gestureDetector = GestureDetector(this, GestureListener())

        homeworkId = intent.getLongExtra("HOMEWORK_ID", 0)
        subjectId = intent.getLongExtra("SUBJECT_ID", 0) // Получаем subjectId из Intent

        if (homeworkId != 0L) {
            homeworkViewModel.getHomeworkById(homeworkId).observe(this) { homework ->
                homework?.let {
                    findViewById<EditText>(R.id.edit_text_description).setText(it.description)
                    findViewById<EditText>(R.id.edit_text_due_date).setText(it.dueDate)
                }
            }
        }

        findViewById<Button>(R.id.button_save).setOnClickListener {
            saveHomework()
        }

        findViewById<Button>(R.id.button_delete).setOnClickListener {
            deleteHomework()
        }
    }

    private fun saveHomework() {
        val description = findViewById<EditText>(R.id.edit_text_description).text.toString()
        val dueDate = findViewById<EditText>(R.id.edit_text_due_date).text.toString()

        val homework = Homework(homeworkId, subjectId, description, dueDate) // Используем subjectId
        if (homeworkId == 0L) {
            homeworkViewModel.insertHomework(homework)
        } else {
            homeworkViewModel.updateHomework(homework)
        }
        finish()
    }

    private fun deleteHomework() {
        if (homeworkId != 0L) {
            val homework = Homework(homeworkId, subjectId, "", "") // Используем subjectId
            homeworkViewModel.deleteHomework(homework)
        }
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
        // Завершение текущей Activity, чтобы вернуться на HomeworkListActivity
        finish()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Передаем события касания GestureDetector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}
