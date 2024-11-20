package com.mobdeve.s20.group.one.mco2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s20.group.one.mco2.databinding.FragmentJournalBinding


class JournalFragment : Fragment() {

    private lateinit var binding: FragmentJournalBinding

    //private val journalList: List<Journal> = DataJournalGenerator.generateJournalData()
    private lateinit var db: DbHelper
    //private lateinit var recyclerView: RecyclerView
    private lateinit var journalAdapter: JournalAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentJournalBinding.inflate(inflater, container, false)

        db = DbHelper(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        recyclerView = binding.rvJournal
//        recyclerView.adapter = JournalAdapter(journalList)
//        recyclerView.layoutManager = LinearLayoutManager(context)
        journalAdapter = JournalAdapter(db.getAllJournals(), requireContext())
        binding.rvJournal.adapter = journalAdapter
        binding.rvJournal.layoutManager = LinearLayoutManager(context)



        binding.btnAddJournal.setOnClickListener {
            Log.d("MainActivity", "Journal button clicked")
            Toast.makeText(context, "Add Journal clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), AddJournalActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        journalAdapter.refreshData(db.getAllJournals())
    }


}