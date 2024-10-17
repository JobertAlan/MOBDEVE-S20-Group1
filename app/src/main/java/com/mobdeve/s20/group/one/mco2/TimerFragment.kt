package com.mobdeve.s20.group.one.mco2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.mobdeve.s20.group.one.mco2.databinding.FragmentTimerBinding


class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentTimerBinding.inflate(inflater, container, false)

        // Implement logic for setting timer
        binding.btnPopupTimer.setOnClickListener {
            Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show()

            // Ideally this should open up a popup window similar to what you did before where
            // it asks the user for hours/mins/sec for the initial timer then the break timer
        }

        //return timerView
        return binding.root
    }
}