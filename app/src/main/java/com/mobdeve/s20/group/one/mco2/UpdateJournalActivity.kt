package com.mobdeve.s20.group.one.mco2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobdeve.s20.group.one.mco2.databinding.ActivityUpdateJournalBinding

class UpdateJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateJournalBinding
    private lateinit var db: DbHelper
    private var journalId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DbHelper(this)

        journalId = intent.getIntExtra("journalId", -1)
        if (journalId == -1) {
            finish()
            return
        }

        val journal = db.getJournalById(journalId)
        if (journal == null) {
            finish()
            return
        }

        binding.tvJournalDate.text = journal.date
        binding.tvUpdateJournalTitle.text = journal.title
        binding.etUpdateJournalContent.text = journal.content
        binding.etUpdateJournalScore.text = journal.empathyScore.toString()

        binding.btnDeleteJournal.setOnClickListener {
            db.deleteJournal(journalId)
            finish()
            Toast.makeText(this, "Journal deleted successfully", Toast.LENGTH_SHORT).show()
        }


    }
}