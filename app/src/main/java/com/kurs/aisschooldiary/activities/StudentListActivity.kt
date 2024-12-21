package com.kurs.aisschooldiary.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.ui.StudentDetailActivity
import com.kurs.aisschooldiary.viewmodel.StudentViewModel

class StudentListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var studentAdapter: ArrayAdapter<String>
    private val studentViewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        listView = findViewById(R.id.list_view_students)
        val studentNames = mutableListOf<String>()

        // Инициализация адаптера для ListView
        studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentNames)
        listView.adapter = studentAdapter

        findViewById<Button>(R.id.button_add_student).setOnClickListener {
            openStudentDetail(0) // 0 для добавления нового студента
        }

        // Запрашиваем список студентов из ViewModel
        studentViewModel.getAllStudents() // Запускаем загрузку студентов
        studentViewModel.students.observe(this) { students ->
            // Обновляем список имен студентов
            studentNames.clear()
            studentNames.addAll(students.map { "${it.surname} ${it.name} ${it.patronym}" })
            studentAdapter.notifyDataSetChanged()
        }

        // Обработка нажатия на элемент списка
        listView.setOnItemClickListener { _, _, position, _ ->
            val studentId = studentViewModel.students.value?.get(position)?.studentId
            studentId?.let { openStudentDetail(it) }
        }
    }

    private fun openStudentDetail(studentId: Long) {
        val intent = Intent(this, StudentDetailActivity::class.java)
        intent.putExtra("STUDENT_ID", studentId)
        startActivity(intent)
    }
}
