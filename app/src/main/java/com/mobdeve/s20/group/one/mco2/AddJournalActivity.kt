package com.mobdeve.s20.group.one.mco2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s20.group.one.mco2.databinding.ActivityAddJournalAcitivtyBinding
import java.util.Calendar

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
            val score = binding.etJournalScore.text.toString().toDouble()
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