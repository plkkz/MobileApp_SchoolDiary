package com.kurs.aisschooldiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.activities.ClassnameListActivity
import com.kurs.aisschooldiary.activities.GradeListActivity
import com.kurs.aisschooldiary.activities.HomeworkListActivity
import com.kurs.aisschooldiary.activities.ScheduleListActivity
import com.kurs.aisschooldiary.activities.StudentListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация кнопок
        val buttonClassnames: Button = findViewById(R.id.button_classnames)
        val buttonGrades: Button = findViewById(R.id.button_grades)
        val buttonHomeworks: Button = findViewById(R.id.button_homeworks)
        val buttonSchedules: Button = findViewById(R.id.button_schedules)
        val buttonStudents: Button = findViewById(R.id.button_students)

        // Установка обработчиков нажатий для кнопок
        buttonClassnames.setOnClickListener {
            startActivity(Intent(this, ClassnameListActivity::class.java))
        }

        buttonGrades.setOnClickListener {
            startActivity(Intent(this, GradeListActivity::class.java))
        }

        buttonHomeworks.setOnClickListener {
            startActivity(Intent(this, HomeworkListActivity::class.java))
        }

        buttonSchedules.setOnClickListener {
            startActivity(Intent(this, ScheduleListActivity::class.java))
        }

        buttonStudents.setOnClickListener {
            startActivity(Intent(this, StudentListActivity::class.java))
        }
    }
}
