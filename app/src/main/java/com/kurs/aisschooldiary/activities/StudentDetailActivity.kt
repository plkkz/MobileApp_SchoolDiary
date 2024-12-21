package com.kurs.aisschooldiary.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.models.Student
import com.kurs.aisschooldiary.viewmodel.StudentViewModel

class StudentDetailActivity : AppCompatActivity() {
    private val studentViewModel: StudentViewModel by viewModels()
    private var studentId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        studentId = intent.getLongExtra("STUDENT_ID", 0)

        if (studentId != 0L) {
            studentViewModel.getStudentById(studentId).observe(this) { student ->
                student?.let {
                    findViewById<EditText>(R.id.edit_text_surname).setText(it.surname)
                    findViewById<EditText>(R.id.edit_text_name).setText(it.name)
                    findViewById<EditText>(R.id.edit_text_patronym).setText(it.patronym)
                    findViewById<EditText>(R.id.edit_text_phone).setText(it.phoneNumber)
                }
            }
        }

        findViewById<Button>(R.id.button_save).setOnClickListener {
            saveStudent()
        }

        findViewById<Button>(R.id.button_delete).setOnClickListener {
            deleteStudent()
        }
    }

    private fun saveStudent() {
        val surname = findViewById<EditText>(R.id.edit_text_surname).text.toString()
        val name = findViewById<EditText>(R.id.edit_text_name).text.toString()
        val patronym = findViewById<EditText>(R.id.edit_text_patronym).text.toString()
        val phone = findViewById<EditText>(R.id.edit_text_phone).text.toString()

        val student = Student(studentId, surname, name, patronym, phone, 0) // Замените 0 на нужный classnameId
        if (studentId == 0L) {
            studentViewModel.insertStudent(student)
        } else {
            studentViewModel.updateStudent(student)
        }
        finish()
    }

    private fun deleteStudent() {
        val student = Student(studentId, "", "", "", "", 0) // Замените 0 на нужный classnameId
        studentViewModel.deleteStudent(student)
        finish()
    }
}
