package com.mobdeve.s20.group.one.mco2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Stores constants to be used in queries
    companion object {
        private const val DATABASE_NAME = "timer_journal_tasks.db"
        private const val DATABASE_VERSION = 1
            private const val TABLE_TASKS = "all_tasks"
        private const val COLUMN_TASKS_ID = "task_id"
        private const val COLUMN_TASKS_TITLE = "task_title"
        private const val COLUMN_TASKS_DESCRIPTION = "task_description"
        private const val COLUMN_TASKS_STATUS = "task_status"
            private const val TABLE_JOURNAL = "all_journals"
        private const val COLUMN_JOURNAL_ID = "journal_id"
        private const val COLUMN_JOURNAL_TITLE = "journal_title"
        private const val COLUMN_JOURNAL_DATE = "journal_date"
        private const val COLUMN_JOURNAL_CONTENT = "journal_content"
        private const val COLUMN_JOURNAL_SCORE = "journal_score"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableTasksQuery = "CREATE TABLE $TABLE_TASKS ($COLUMN_TASKS_ID INTEGER PRIMARY KEY, " +
                                                                "$COLUMN_TASKS_TITLE TEXT, " +
                                                                "$COLUMN_TASKS_DESCRIPTION TEXT , " +
                                                                "$COLUMN_TASKS_STATUS TEXT)"
        val createTableJournalQuery = "CREATE TABLE $TABLE_JOURNAL ($COLUMN_JOURNAL_ID INTEGER PRIMARY KEY, " +
                                                                    "$COLUMN_JOURNAL_TITLE TEXT, " +
                                                                    "$COLUMN_JOURNAL_DATE TEXT, " +
                                                                    "$COLUMN_JOURNAL_CONTENT TEXT, " +
                                                                    "$COLUMN_JOURNAL_SCORE REAL)"
        db?.execSQL(createTableTasksQuery)
        db?.execSQL(createTableJournalQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_JOURNAL")
        onCreate(db)
    }

    // CRUD OPERATIONS for Task

    fun insertTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASKS_TITLE, task.title)
            put(COLUMN_TASKS_DESCRIPTION, task.description)
            put(COLUMN_TASKS_STATUS, task.status)
        }
        db.insert(TABLE_TASKS, null, values)
        db.close()
    }

    fun getAllTasks(): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_TASKS"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()) {
            val taskId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASKS_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASKS_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASKS_DESCRIPTION))
            val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASKS_STATUS))

            val task = Task(taskId, title, description, status)
            taskList.add(task)
        }
        cursor.close()
        db.close()

        return taskList
    }

    fun getTaskById(taskId: Int): Task {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_TASKS WHERE $COLUMN_TASKS_ID = $taskId"

        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASKS_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASKS_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASKS_DESCRIPTION))
        val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASKS_STATUS))

        cursor.close()
        db.close()

        return Task(id, title, description, status)
    }

    fun updateTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASKS_TITLE, task.title)
            put(COLUMN_TASKS_DESCRIPTION, task.description)
            put(COLUMN_TASKS_STATUS, task.status)
        }
        val whereClause = "$COLUMN_TASKS_ID = ?"
        val whereArgs = arrayOf(task.taskId.toString())
        db.update(TABLE_TASKS, values, whereClause, whereArgs)
        db.close()
    }

    fun deleteTask(taskId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_TASKS_ID = ?"
        val whereArgs = arrayOf(taskId.toString())
        db.delete(TABLE_TASKS, whereClause, whereArgs)
        db.close()
    }


    // CRUD OPERATIONS for Journal

    fun insertJournal(journal: Journal) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_JOURNAL_TITLE, journal.title)
            put(COLUMN_JOURNAL_DATE, journal.date)
            put(COLUMN_JOURNAL_CONTENT, journal.content)
            put(COLUMN_JOURNAL_SCORE, journal.empathyScore)
        }
        db.insert(TABLE_JOURNAL, null, values)
        db.close()
    }

    fun getAllJournals(): List<Journal> {
        val journalList = mutableListOf<Journal>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_JOURNAL"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()) {
            val journalId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_TITLE))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_DATE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_CONTENT))
            val empathyScore = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_SCORE))

            val journal = Journal(journalId, title, date, content, empathyScore)
            journalList.add(journal)
        }
        cursor.close()
        db.close()

        return journalList
    }

    fun getJournalById(journalId: Int): Journal {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_JOURNAL WHERE $COLUMN_JOURNAL_ID = $journalId"

        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_TITLE))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_DATE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_CONTENT))
        val score = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_JOURNAL_SCORE))

        cursor.close()
        db.close()
        return Journal(id, title, date, content, score)
    }

    fun deleteJournal(journalId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_JOURNAL_ID = ?"
        val whereArgs = arrayOf(journalId.toString())
        db.delete(TABLE_JOURNAL, whereClause, whereArgs)
        db.close()
    }

//    Functions for Notifs
fun getAllJournalCounts(): Map<String, Int> {
    val counts = mutableMapOf("positive" to 0, "negative" to 0, "neutral" to 0)
    val db = readableDatabase

    // Query to count occurrences of each sentiment score
    val query = """
        SELECT $COLUMN_JOURNAL_SCORE, COUNT($COLUMN_JOURNAL_SCORE)
        FROM $TABLE_JOURNAL
        GROUP BY $COLUMN_JOURNAL_SCORE
    """
    val cursor = db.rawQuery(query, null)

    if (cursor.moveToFirst()) {
        do {
            val score = cursor.getDouble(0)
            val count = cursor.getInt(1)

            when (score) {
                1.0 -> counts["positive"] = count
                0.0 -> counts["neutral"] = count
                2.0 -> counts["negative"] = count
            }
        } while (cursor.moveToNext())
    }

    cursor.close()
    db.close()
    return counts
}




    fun getTaskCount(): Int {
        val db = readableDatabase
        val query = "SELECT COUNT(*) FROM Task"
        val cursor = db.rawQuery(query, null)
        var count = 0

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()
        db.close()
        return count
    }

    fun getIncompleteTasks(): MutableList<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
//        val query = "SELECT id, name FROM Task WHERE completed = 0"
        val query = "SELECT id, name FROM Task WHERE status != 'done'"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val description = cursor.getString(2)
                val status = cursor.getString(3)
                tasks.add(Task(id, name, description, status))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return tasks
    }

    fun isJournalWrittenToday(): Boolean {
        val db = readableDatabase
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val todayDate = sdf.format(Date())

        val query = "SELECT COUNT(*) FROM Journal WHERE date = ?"
        val cursor = db.rawQuery(query, arrayOf(todayDate))
        var count = 0

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }

        cursor.close()
        db.close()
        return count > 0
    }


}