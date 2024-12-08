package com.mobdeve.s20.group.one.mco2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobdeve.s20.group.one.mco2.databinding.ActivityUpdateTaskBinding

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db: DbHelper
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        taskId = intent.getIntExtra("taskId", -1)
        if (taskId == -1) {
            finish()
            return
        }

        val task = db.getTaskById(taskId)
        binding.etUpdateTaskTitle.setText(task.title)
        binding.etUpdateTaskDescription.setText(task.description)
        //binding.etUpdateTaskProgress.setText(task.status)

        //Spinner Setup
        val spinnerSetup = SpinnerSetup(this, binding.spinnerProgress)
        spinnerSetup.initializeSpinner()

        val status = task.status
        val position = (0 until binding.spinnerProgress.adapter.count).find {
            binding.spinnerProgress.adapter.getItem(it).toString() == status
        } ?: 0

        binding.spinnerProgress.setSelection(position)

        binding.btnUpdateTask.setOnClickListener {
            val newTitle = binding.etUpdateTaskTitle.text.toString()
            val newDescription = binding.etUpdateTaskDescription.text.toString()
            //val newStatus = binding.etUpdateTaskProgress.text.toString()
            val newStatus = binding.spinnerProgress.selectedItem.toString()

            db.updateTask(Task(taskId, newTitle, newDescription, newStatus))
            finish()
            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()
        }

        binding.btnDeleteTask.setOnClickListener {
            db.deleteTask(taskId)
            finish()
            Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show()
        }

    }
}