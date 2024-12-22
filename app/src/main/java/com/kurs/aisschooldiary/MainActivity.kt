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
        buttonClassnames.setOnClickListener { launchActivity(ClassnameListActivity::class.java) }
        buttonGrades.setOnClickListener { launchActivity(GradeListActivity::class.java) }
        buttonHomeworks.setOnClickListener { launchActivity(HomeworkListActivity::class.java) }
        buttonSchedules.setOnClickListener { launchActivity(ScheduleListActivity::class.java) }
        buttonStudents.setOnClickListener { launchActivity(StudentListActivity::class.java) }
    }

    // Функция для запуска активности
    private fun <T> launchActivity(activityClass: Class<T>) {
        try {
            val intent = Intent(this, activityClass)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace() // Логирование ошибки, если что-то пошло не так
        }
    }
}
