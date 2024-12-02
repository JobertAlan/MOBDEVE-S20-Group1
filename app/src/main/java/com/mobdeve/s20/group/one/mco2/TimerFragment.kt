package com.mobdeve.s20.group.one.mco2

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.mobdeve.s20.group.one.mco2.databinding.FragmentTimerBinding
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class TimerFragment : Fragment() {

    private var timeSelected : Int = 0
    private var timeCountDown: CountDownTimer? = null
    private var timeProgress = 0
    private var pauseOffSet: Long = 0
    private var isStart = true


    private lateinit var binding: FragmentTimerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentTimerBinding.inflate(inflater, container, false)

        val addTimerButton = binding.btnAddTimer
        val startTimerButton = binding.btnStartTimer
        val resetTimerButton = binding.btnResetTimer
        val addFifteenButton = binding.btnAddFifteen

        //return timerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Implement logic for setting timer
        binding.btnAddTimer.setOnClickListener {
            setTimeFunction()
        }
        binding.btnStartTimer.setOnClickListener {
            startTimerSetup()
        }
        binding.btnResetTimer.setOnClickListener {
            resetTimer()
        }
        binding.btnAddFifteen.setOnClickListener {
            addExtraTime()
        }
    }

    private fun addExtraTime() {
        val progressBar: ProgressBar = binding.progressBar
        if (timeSelected != 0) {
            timeSelected += 15
            timerPause()
            startTimer(pauseOffSet)
            Toast.makeText(context, "Added 15 seconds", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetTimer() {
        if (timeCountDown != null ) {
            timeCountDown!!.cancel()
            timeProgress = 0
            timeSelected = 0
            pauseOffSet = 0
            timeCountDown = null
            val startButton = binding.btnStartTimer
            startButton.text = "Start"
            isStart = true
            val progressBar = binding.progressBar
            progressBar.progress = 0
            val tvTimeLeft = binding.tvTimeLeft
            tvTimeLeft.text = "0"
        }
    }

    private fun timerPause() {
        if (timeCountDown != null) {
            timeCountDown!!.cancel()

        }

    }

    private fun startTimerSetup() {
        val startBtn: Button = binding.btnStartTimer
        if (timeSelected > timeProgress) {
            if (isStart) {
                startBtn.text = "Pause"
                startTimer(pauseOffSet)
                isStart = false
            }
            else {
                isStart = true
                startBtn.text = "Resume"
                timerPause()
            }
        }
        else {
            Toast.makeText(context, "Set a timer", Toast.LENGTH_SHORT).show()
        }

    }

    private fun startTimer(pauseOffSetL: Long) {
        val progressBar: ProgressBar = binding.progressBar
        progressBar.progress = timeProgress
        timeCountDown = object :CountDownTimer(
            (timeSelected*1000).toLong() - pauseOffSetL*1000, 1000)
        {
            override fun onTick(p0: Long) {
                timeProgress++
                pauseOffSet = timeSelected.toLong()- p0/1000
                progressBar.progress = timeSelected-timeProgress
                val timeLeftTv: TextView = binding.tvTimeLeft
                timeLeftTv.text = (timeSelected - timeProgress).toString()
            }

            override fun onFinish() {
                resetTimer()
                Toast.makeText(context ,"Times Up!", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }

    private fun setTimeFunction()
    {
        val timeDialog = context?.let { Dialog(it) }
        timeDialog?.setContentView(R.layout.add_timer_dialouge)
        val timeSet = timeDialog?.findViewById<EditText>(R.id.etGetTime)
        val timeLeftTv: TextView = binding.tvTimeLeft
        val btnStart: Button = binding.btnStartTimer
        val progressBar = binding.progressBar
        timeDialog?.findViewById<Button>(R.id.btnOk)?.setOnClickListener {
            if (timeSet?.text?.isEmpty() == true)
            {
                Toast.makeText(context,"Enter Time Duration",Toast.LENGTH_SHORT).show()
            }
            else
            {
                resetTimer()
                timeLeftTv.text = timeSet?.text
                btnStart.text = "Start"
                timeSelected = timeSet?.text.toString().toInt()
                progressBar.max = timeSelected
            }
            timeDialog.dismiss()
        }
        timeDialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()


        if (timeCountDown != null ) {
            timeCountDown?.cancel()
            timeProgress = 0
        }
    }

}