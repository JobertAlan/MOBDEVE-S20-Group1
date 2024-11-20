package com.mobdeve.s20.group.one.mco2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class JournalAdapter(private var journalList: List<Journal>, context: Context):
    RecyclerView.Adapter<JournalAdapter.JournalHolder>() {

    class JournalHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val tvJournalTitle: TextView = itemView.findViewById(R.id.tvJournalTitle)
        private val tvJournalDate: TextView = itemView.findViewById(R.id.tvJournalDate)
        private val tvIdJournal: TextView = itemView.findViewById(R.id.tvIdJournal)

        fun bindData(journal: Journal) {
            tvJournalTitle.text = journal.title
            tvJournalDate.text = journal.date
            tvIdJournal.text = journal.journalId.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.journal_layout, parent, false)

        return JournalHolder(view)
    }

    override fun onBindViewHolder(holder: JournalHolder, position: Int) {

        holder.bindData(journalList[position])

        val journal = journalList[position]


        // This is what will be setting the intent to view the different journals ideally
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Journal ${journal.title} clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    fun refreshData(newJournals: List<Journal>) {
        journalList = newJournals
        notifyDataSetChanged()

    }

}
