package com.mobdeve.s20.group.one.mco2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mobdeve.s20.group.one.mco2.databinding.FragmentTaskBinding


class TaskFragment : Fragment(R.layout.fragment_task) {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddTask.setOnClickListener {
            Log.d("MainActivity", "Task button clicked")

            Toast.makeText(context, "Add Task clicked", Toast.LENGTH_SHORT).show()
        }

    }
}