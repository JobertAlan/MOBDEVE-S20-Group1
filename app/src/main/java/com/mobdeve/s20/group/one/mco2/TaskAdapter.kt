package com.mobdeve.s20.group.one.mco2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class TaskAdapter(private val taskList: ArrayList<Task>):
    Adapter<TaskHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_layout, parent, false)

        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {

        holder.bindData(taskList[position])

        val task = taskList[position]

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Task ${task.title} clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}


class TaskHolder(itemView: View): ViewHolder(itemView) {

    private val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
    private val tvTaskProgress: TextView = itemView.findViewById(R.id.tvTaskProgress)
    private val tvIdTask: TextView = itemView.findViewById(R.id.tvIdTask)

    fun bindData(task: Task) {
        tvTaskTitle.text = task.title
        tvTaskProgress.text = task.status
        tvIdTask.text = task.taskId.toString()
    }
}