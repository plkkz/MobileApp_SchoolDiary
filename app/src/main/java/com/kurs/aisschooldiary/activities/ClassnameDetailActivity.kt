package com.kurs.aisschooldiary.activities

import android.os.Bundle
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
    }

    private fun saveClassname() {
        val classnameName = findViewById<EditText>(R.id.edit_text_classname).text.toString()
        val classname = Classname(classnameId, classnameName) // Замените 0 на нужный classnameId
        if (classnameId == 0L) {
            classnameViewModel.insert(classname)
        } else {
            classnameViewModel.update(classname)
        }
        finish()
    }

    private fun deleteClassname() {
        val classname = Classname(classnameId, "") // Пустое имя для удаления
        classnameViewModel.delete(classname)
        Toast.makeText(this, "Класс удален", Toast.LENGTH_SHORT).show()
        finish()
    }
}
