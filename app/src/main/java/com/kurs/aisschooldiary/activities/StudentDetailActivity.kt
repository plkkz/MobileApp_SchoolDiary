package com.kurs.aisschooldiary.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.models.Classname
import com.kurs.aisschooldiary.models.Student
import com.kurs.aisschooldiary.viewmodel.ClassnameViewModel
import com.kurs.aisschooldiary.viewmodel.StudentViewModel

class StudentDetailActivity : AppCompatActivity() {
    private val studentViewModel: StudentViewModel by viewModels()
    private val classnameViewModel: ClassnameViewModel by viewModels()
    private var studentId: Long = 0
    private lateinit var classnameSpinner: Spinner
    private lateinit var classnames: List<Classname>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        classnameSpinner = findViewById(R.id.spinner_classname)

        studentId = intent.getLongExtra("STUDENT_ID", 0)

        // Получаем список классов
        classnameViewModel.getAllClassnames()
        classnameViewModel.classnames.observe(this) { classnames ->
            this.classnames = classnames
            setupSpinner(classnames)
        }

        if (studentId != 0L) {
            studentViewModel.getStudentById(studentId).observe(this) { student ->
                student?.let {
                    findViewById<EditText>(R.id.edit_text_surname).setText(it.surname)
                    findViewById<EditText>(R.id.edit_text_name).setText(it.name)
                    findViewById<EditText>(R.id.edit_text_patronym).setText(it.patronym)
                    findViewById<EditText>(R.id.edit_text_phone).setText(it.phoneNumber)

                    // Устанавливаем выбранный класс в Spinner
                    val position = classnames.indexOfFirst { it.classnameId == it.classnameId }
                    classnameSpinner.setSelection(position)
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

    private fun setupSpinner(classnames: List<Classname>) {
        val classNamesList = classnames.map { it.className }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classNamesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classnameSpinner.adapter = adapter
    }

    private fun saveStudent() {
        val surname = findViewById<EditText>(R.id.edit_text_surname).text.toString()
        val name = findViewById<EditText>(R.id.edit_text_name).text.toString()
        val patronym = findViewById<EditText>(R.id.edit_text_patronym).text.toString()
        val phone = findViewById<EditText>(R.id.edit_text_phone).text.toString()

        // Получаем выбранный класс
        val selectedClassName = classnameSpinner.selectedItem.toString()
        val selectedClass = classnames.find { it.className == selectedClassName }
        val classnameId = selectedClass?.classnameId ?: 0 // Получаем ID класса

        val student = Student(studentId, surname, name, patronym, phone, classnameId)
        if (studentId == 0L) {
            studentViewModel.insertStudent(student)
        } else {
            studentViewModel.updateStudent(student)
        }
        finish()
    }

    private fun deleteStudent() {
        if (studentId != 0L) {
            // Получаем текущего студента для удаления
            studentViewModel.getStudentById(studentId).observe(this) { student ->
                student?.let {
                    // Удаляем студента по его ID
                    studentViewModel.deleteStudent(it)
                    finish()
                } ?: run {
                    Toast.makeText(this, "Ошибка: Студент не найден", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Ошибка: Студент не выбран для удаления", Toast.LENGTH_SHORT).show()
        }
    }

}
