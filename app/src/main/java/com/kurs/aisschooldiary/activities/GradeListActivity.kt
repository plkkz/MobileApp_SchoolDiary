package com.kurs.aisschooldiary.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.viewmodel.GradeViewModel

class GradeListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var gradeAdapter: ArrayAdapter<String>
    private val gradeViewModel: GradeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade)

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

    private fun openGradeDetail(gradeId: Long) {
        val intent = Intent(this, GradeDetailActivity::class.java)
        intent.putExtra("GRADE_ID", gradeId)
        startActivity(intent)
    }
}

