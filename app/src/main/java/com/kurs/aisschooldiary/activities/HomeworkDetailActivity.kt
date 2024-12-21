package com.kurs.aisschooldiary.ui

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework_detail)

        homeworkId = intent.getLongExtra("HOMEWORK_ID", 0)

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

        val homework = Homework(homeworkId, 0, description, dueDate) // Замените 0 на нужный subjectId
        if (homeworkId == 0L) {
            homeworkViewModel.insertHomework(homework)
        } else {
            homeworkViewModel.updateHomework(homework)
        }
        finish()
    }

    private fun deleteHomework() {
        val homework = Homework(homeworkId, 0, "", "") // Замените 0 на нужный subjectId
        homeworkViewModel.deleteHomework(homework)
        finish()
    }
}
