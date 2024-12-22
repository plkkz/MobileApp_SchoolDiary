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
import com.kurs.aisschooldiary.models.Classname
import com.kurs.aisschooldiary.viewmodel.ClassnameViewModel

class ClassnameListActivity : AppCompatActivity() {
    private lateinit var listViewClassnames: ListView
    private lateinit var classnameAdapter: ArrayAdapter<String>
    private val classnameViewModel: ClassnameViewModel by viewModels()
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classname)

        // Инициализация GestureDetector
        gestureDetector = GestureDetector(this, GestureListener())

        listViewClassnames = findViewById(R.id.list_view_classnames)
        val classnameNames = mutableListOf<String>()
        classnameAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, classnameNames)
        listViewClassnames.adapter = classnameAdapter

        findViewById<Button>(R.id.button_add_classname).setOnClickListener {
            openClassnameDetail(0) // 0 для добавления нового класса
        }

        // Запрашиваем список классов из ViewModel
        classnameViewModel.getAllClassnames() // Запускаем загрузку классов
        classnameViewModel.classnames.observe(this) { classnames ->
            classnameNames.clear()
            classnameNames.addAll(classnames.map { it.className }) // Предполагается, что у класса есть поле className
            classnameAdapter.notifyDataSetChanged()
        }

        // Обработка нажатия на элемент списка
        listViewClassnames.setOnItemClickListener { _, _, position, _ ->
            val classnameId = classnameViewModel.classnames.value?.get(position)?.classnameId
            classnameId?.let { openClassnameDetail(it) }
        }
    }

    private fun openClassnameDetail(classnameId: Long) {
        val intent = Intent(this, ClassnameDetailActivity::class.java)
        intent.putExtra("classnameId", classnameId)
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
        finish() // Закрываем текущую активность
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Передаем события касания GestureDetector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}
