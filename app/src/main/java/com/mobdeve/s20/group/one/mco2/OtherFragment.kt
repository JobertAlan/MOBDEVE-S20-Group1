package com.mobdeve.s20.group.one.mco2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s20.group.one.mco2.databinding.FragmentOtherBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class OtherFragment : Fragment() {

    private var _binding: FragmentOtherBinding? = null
    private val binding get() = _binding!!

    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var dbHelper: DbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherBinding.inflate(inflater, container, false)

        dbHelper = DbHelper(requireContext())
        setupRecyclerView()
        setupTesterButton()

        // Generate daily notifications
        generateDailyNotifications()

        return binding.root
    }

    private fun setupRecyclerView() {
        notificationAdapter = NotificationAdapter(mutableListOf())
        binding.rvNotifications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationAdapter
        }
    }

    private fun setupTesterButton() {
        binding.btnNotifTester.setOnClickListener {
            generateWeeklyNotification()
            Toast.makeText(requireContext(), "Weekly journal sentiment updated!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateWeeklyNotification() {
        // Fetch journal sentiments from the last week
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val startDate = calendar.time

        val journalStats = dbHelper.getJournalStatsSince(startDate)

        // Determine which sentiment is most prevalent
        val message = when {
            journalStats["positive"] ?: 0 > journalStats["negative"] ?: 0 &&
                    journalStats["positive"] ?: 0 > journalStats["neutral"] ?: 0 -> {
                "Great job! Most of your journal entries were positive this week!"
            }
            journalStats["negative"] ?: 0 > journalStats["positive"] ?: 0 &&
                    journalStats["negative"] ?: 0 > journalStats["neutral"] ?: 0 -> {
                "Hang in there. It seems like this week had more challenges than usual."
            }
            else -> {
                "Your journal entries this week were quite balanced. Keep it up!"
            }
        }

        // Add the notification
        addNotification("Weekly Journal Summary", message)
    }

    private fun generateDailyNotifications() {
        val taskCount = dbHelper.getTaskCount()
        val tasks = dbHelper.getIncompleteTasks()
        val journalWrittenToday = dbHelper.isJournalWrittenToday()

        // Reminder for daily tasks
        //problem here
        tasks.forEach { task ->
            addNotification("Task Reminder", "Reminder to finish the task '${task.name}'!")
        }

        // Special notification if there are more than 5 tasks
        if (taskCount > 5) {
            addNotification(
                "Task Overload",
                "You have over 5 tasks pending. Remember to take things one step at a time!"
            )
        }

        // Notification if no journal entry has been written today
        if (!journalWrittenToday) {
            addNotification(
                "Write Your Journal",
                "You haven’t written a journal entry today. Reflect on your day and write your thoughts!"
            )
        }
    }

    private fun addNotification(title: String, message: String) {
        val currentDate = Calendar.getInstance()
        val date = "${currentDate.get(Calendar.MONTH) + 1}/${currentDate.get(Calendar.DAY_OF_MONTH)}/${currentDate.get(Calendar.YEAR)}"
        val time = "${currentDate.get(Calendar.HOUR_OF_DAY)}:${currentDate.get(Calendar.MINUTE)}"

        // Add notification to the RecyclerView
        notificationAdapter.addNotification(NotificationItem(date, time, message))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
