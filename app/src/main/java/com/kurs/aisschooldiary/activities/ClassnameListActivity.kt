package com.kurs.aisschooldiary.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurs.aisschooldiary.R
import com.kurs.aisschooldiary.models.Classname
import com.kurs.aisschooldiary.viewmodel.ClassnameViewModel

class ClassnameListActivity : AppCompatActivity() {
    private lateinit var listViewClassnames: ListView
    private lateinit var classnameAdapter: ArrayAdapter<String>
    private val classnameViewModel: ClassnameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classname)

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
}
