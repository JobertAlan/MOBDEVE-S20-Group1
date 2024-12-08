package com.mobdeve.s20.group.one.mco2

import android.os.Bundle
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobdeve.s20.group.one.mco2.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        //Spinner Setup
        val spinner: Spinner = findViewById(R.id.spinnerProgress)
        val spinnerSetup = SpinnerSetup(this, spinner)
        spinnerSetup.initializeSpinner()

        binding.btnInsertTask.setOnClickListener {
            val title = binding.etTaskTitle.text.toString()
            val description = binding.etTaskDescription.text.toString()
            //val progress = binding.etTaskProgress.text.toString()
            val progress = binding.spinnerProgress.selectedItem.toString()
            val task = Task(0, title, description, progress)

            db.insertTask(task)
            finish()
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
        }

    }
}