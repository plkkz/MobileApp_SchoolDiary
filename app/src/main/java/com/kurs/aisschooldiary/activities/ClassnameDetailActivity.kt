package com.kurs.aisschooldiary.activities

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.models.Classname
import com.kurs.aisschooldiary.viewmodel.ClassnameViewModel

class ClassnameDetailActivity : AppCompatActivity() {
    private val classnameViewModel: ClassnameViewModel by viewModels()
    private var classnameId: Long = 0
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classname_detail)

        classnameId = intent.getLongExtra("classnameId", 0)

        val editTextClassName = findViewById<EditText>(R.id.edit_text_classname)

        if (classnameId != 0L) {
            classnameViewModel.getClassnameById(classnameId).observe(this) { classname ->
                classname?.let {
                    editTextClassName.setText(it.className)
                }
            }
        }

        findViewById<Button>(R.id.button_save).setOnClickListener {
            saveClassname()
        }

        findViewById<Button>(R.id.button_delete).setOnClickListener {
            deleteClassname()
        }

        // Инициализация GestureDetector
        gestureDetector = GestureDetector(this, GestureListener())
    }

    private fun saveClassname() {
        val classnameName = findViewById<EditText>(R.id.edit_text_classname).text.toString()
        val classname = Classname(classnameId, classnameName)
        if (classnameId == 0L) {
            classnameViewModel.insert(classname)
        } else {
            classnameViewModel.update(classname)
        }
        finish()
    }

    private fun deleteClassname() {
        val classname = Classname(classnameId, "")
        classnameViewModel.delete(classname)
        Toast.makeText(this, "Класс удален", Toast.LENGTH_SHORT).show()
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
        // Переход на ClassnameListActivity
        finish() // Закрываем текущую активность
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Передаем события касания GestureDetector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}
