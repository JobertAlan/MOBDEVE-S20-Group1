package com.mobdeve.s20.group.one.mco2

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

class SpinnerSetup(private val context: Context, private val spinner: Spinner) {

    fun initializeSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.task_progress_options,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }
}
