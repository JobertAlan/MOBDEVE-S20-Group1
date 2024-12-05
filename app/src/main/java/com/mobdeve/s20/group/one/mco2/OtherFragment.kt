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

import com.mobdeve.s20.group.one.mco2.DataNotificationGenerator

class OtherFragment : Fragment() {

    private lateinit var binding: FragmentOtherBinding
    private lateinit var dbHelper: DbHelper
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtherBinding.inflate(inflater, container, false)

        dbHelper = DbHelper(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sampleNotifList = DataNotificationGenerator.generateNotificationData()

        // insert list of notifications
        notificationAdapter = NotificationAdapter(sampleNotifList, requireContext())
        binding.rvNotifications.adapter = notificationAdapter
        binding.rvNotifications.layoutManager = LinearLayoutManager(context)


        //generateDailyNotifications()

        addNotification("test message")

        binding.btnNotifTester.setOnClickListener {

            Toast.makeText(requireContext(), "Weekly journal sentiment updated!", Toast.LENGTH_SHORT).show()

            generateDailyNotificationsBtn()
        }
    }

        private fun addNotification(message: String) {
        val currentDate = Calendar.getInstance()
        val date = "${currentDate.get(Calendar.MONTH) + 1}/${currentDate.get(Calendar.DAY_OF_MONTH)}/${currentDate.get(
            Calendar.YEAR)}"
        val time = "${currentDate.get(Calendar.HOUR_OF_DAY)}:${currentDate.get(Calendar.MINUTE)}"

        // Add notification to the RecyclerView
        notificationAdapter.addNotification(NotificationItem(date, time, message))
    }

    private fun generateDailyNotifications() {
        val taskCount = dbHelper.getTaskCount()

        val tasks = dbHelper.getAllTasks()

//        val test = tasks[1].title

        Toast.makeText(requireContext(), "${tasks[1].title}", Toast.LENGTH_SHORT).show()

    }
    private fun generateDailyNotificationsBtn() {
        // Get journal counts for all journals
        val journalCounts = dbHelper.getAllJournalCounts()

        val posCount = journalCounts["positive"] ?: 0
        val negCount = journalCounts["negative"] ?: 0
        val neutralCount = journalCounts["neutral"] ?: 0

        // If no journals were written, notify the user to create more
        if (posCount == 0 && negCount == 0 && neutralCount == 0) {
            addNotification(
                "Journal Reminder: No journal entries this week. Reflect on your experiences and write your thoughts!"
            )
            return
        }

        // Determine which sentiment is most frequent
        val message = when {
            posCount > negCount && posCount > neutralCount -> {
                "Great week! Most of your journal entries were positive."
            }
            negCount > posCount && negCount > neutralCount -> {
                "It seems like this week had more challenges. Take some time for self-care."
            }
            else -> {
                "Your journal entries were quite balanced this week. Keep it up!"
            }
        }

        // Add notification about the weekly sentiment
        addNotification(message)
    }

}

//        private fun generateDailyNotifications() {
//        val taskCount = dbHelper.getTaskCount()
////        val tasks = dbHelper.getIncompleteTasks()
//
//        val tasks = dbHelper.getAllTasks()
//
//        val journalWrittenToday = dbHelper.isJournalWrittenToday()
//
//        // Reminder for daily tasks
//        //problem here
//        tasks.forEach { task ->
//            addNotification("Task Reminder", "Reminder to finish the task '${task.title}'!")
//        }
//
//        // Special notification if there are more than 5 tasks
//        if (taskCount > 5) {
//            addNotification(
//                "Task Overload",
//                "You have over 5 tasks pending. Remember to take things one step at a time!"
//            )
//        }
//
//        // Notification if no journal entry has been written today
//        if (!journalWrittenToday) {
//            addNotification(
//                "Write Your Journal",
//                "You haven’t written a journal entry today. Reflect on your day and write your thoughts!"
//            )
//        }
//    }



