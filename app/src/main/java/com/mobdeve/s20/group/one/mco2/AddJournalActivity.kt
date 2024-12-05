package com.mobdeve.s20.group.one.mco2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s20.group.one.mco2.databinding.ActivityAddJournalAcitivtyBinding
import java.util.Calendar

import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import kotlinx.coroutines.*


class AddJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddJournalAcitivtyBinding
    private lateinit var db: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddJournalAcitivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        binding.btnInsertJournal.setOnClickListener {
            val title = binding.etJournalTitle.text.toString()
            val content = binding.etJournalContent.text.toString()
            val score = getSentimentScore(content)
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH) + 1
                val day = calendar.get(Calendar.DAY_OF_MONTH)
            val currentDate = "$month/$day/$year"
            val journal = Journal(0, title, currentDate, content, score)
            db.insertJournal(journal)
            finish()
            Toast.makeText(this, "Journal Entry Created", Toast.LENGTH_SHORT).show()
        }

    }
}

private fun getSentimentScore(text: String): Double {
    val url = "http://text-processing.com/api/sentiment/"
    var score = 9.0  // Default: "none/service is down"

    runBlocking {
        val job = launch(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                connection.doOutput = true

                // Send the text as part of the POST payload
                val payload = "text=${text}"
                connection.outputStream.write(payload.toByteArray())

                if (connection.responseCode == 200) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)

                    // Use getString instead of getJSONObject
                    val label = json.getString("label")

                    // Map the label to a double
                    score = when (label) {
                        "pos" -> 1.0
                        "neg" -> 2.0
                        "neutral" -> 0.0
                        else -> 9.0
                    }
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                score = 9.0  // Default to "none/service is down"
            }
        }
        job.join()  // Wait for the coroutine to finish
    }
    return score
}

