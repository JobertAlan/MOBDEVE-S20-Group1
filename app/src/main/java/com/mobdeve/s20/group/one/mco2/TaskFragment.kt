package com.mobdeve.s20.group.one.mco2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s20.group.one.mco2.databinding.FragmentTaskBinding


class TaskFragment : Fragment(R.layout.fragment_task) {

    private lateinit var binding: FragmentTaskBinding
    //private val taskList: ArrayList<Task> = DataTaskGenerator.generateTaskData()
    private lateinit var db: DbHelper
    //private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)

        db = DbHelper(requireContext())


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        recyclerView = binding.rvTask
//        recyclerView.adapter = TaskAdapter(db.getAllTasks(), requireContext())
//        recyclerView.layoutManager = LinearLayoutManager(context)

        taskAdapter = TaskAdapter(db.getAllTasks(), requireContext())
        binding.rvTask.adapter = taskAdapter
        binding.rvTask.layoutManager = LinearLayoutManager(context)



        binding.btnAddTask.setOnClickListener {
            Log.d("MainActivity", "Task button clicked")
            Toast.makeText(context, "Add Task clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), AddTaskActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        taskAdapter.refreshData(db.getAllTasks())

    }
}