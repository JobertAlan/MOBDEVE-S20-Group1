package com.mobdeve.s20.group.one.mco2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class TaskAdapter(private var taskList: List<Task>, context: Context):
    RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    class TaskHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        private val tvTaskProgress: TextView = itemView.findViewById(R.id.tvTaskProgress)
        private val tvIdTask: TextView = itemView.findViewById(R.id.tvIdTask)

        fun bindData(task: Task) {
            tvTaskTitle.text = task.title
            tvTaskProgress.text = task.status
            tvIdTask.text = task.taskId.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_layout, parent, false)

        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {

        holder.bindData(taskList[position])

        val task = taskList[position]


        // This is what will be setting the intent to view the different tasks in detail
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Task ${task.title} clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java)
            intent.putExtra("taskId", task.taskId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun refreshData(newTasks: List<Task>) {
        taskList = newTasks
        notifyDataSetChanged()

    }
}


