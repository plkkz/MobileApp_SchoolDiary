package com.kurs.aisschooldiary.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.ui.HomeworkDetailActivity
import com.kurs.aisschooldiary.viewmodel.HomeworkViewModel

class HomeworkListActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var homeworkAdapter: ArrayAdapter<String>
    private val homeworkViewModel: HomeworkViewModel by viewModels()

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
    }

    private fun openHomeworkDetail(homeworkId: Long) {
        val intent = Intent(this, HomeworkDetailActivity::class.java)
        intent.putExtra("HOMEWORK_ID", homeworkId)
        startActivity(intent)
    }
}
