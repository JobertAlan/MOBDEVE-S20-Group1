package com.mobdeve.s20.group.one.mco2

class DataTaskGenerator {
    companion object {
        fun generateTaskData(): ArrayList<Task> {
            val taskList = arrayListOf(
                Task(
                    taskId = 1,
                    title = "Complete Pomodoro Timer UI",
                    description = "Finish designing the user interface for the Pomodoro timer app.",
                    status = "In Progress"
                ),
                Task(
                    taskId = 2,
                    title = "Write Journal Feature",
                    description = "Implement the journal feature for users to log their activities.",
                    status = "Pending"
                ),
                Task(
                    taskId = 3,
                    title = "Test Notification System",
                    description = "Ensure the timer app’s notification system works across all devices.",
                    status = "Completed"
                )
            )

            return taskList
        }
    }
}