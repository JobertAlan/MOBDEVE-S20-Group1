package com.mobdeve.s20.group.one.mco2

class DataJournalGenerator {
    companion object {
        fun generateJournalData(): ArrayList<Journal> {

            val journalList = arrayListOf(
                Journal(
                    journalId = 1,
                    title = "A Day in Nature",
                    date = "2024-10-10",
                    content = "Spent the entire day hiking through the mountains. The fresh air and scenic views were a perfect escape from daily stress.",
                    empathyScore = 7.8
                ),
                Journal(
                    journalId = 2,
                    title = "Challenges at Work",
                    date = "2024-10-11",
                    content = "Work has been overwhelming this week. Deadlines are piling up, and the pressure is starting to get to me.",
                    empathyScore = 5.4
                ),
                Journal(
                    journalId = 3,
                    title = "Reconnecting with Friends",
                    date = "2024-10-12",
                    content = "Had a wonderful evening catching up with old friends. It reminded me of how important these connections are.",
                    empathyScore = 8.2
                )
            )

            return journalList
        }
    }
}