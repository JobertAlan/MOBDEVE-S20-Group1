package com.mobdeve.s20.group.one.mco2

import android.app.Dialog
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class TimerViewModel : ViewModel() {

    public var timeSelected : Int = 0
    public var timer: CountDownTimer? = null
    public var pauseOffSet: Long = 0

    var timerValue = MutableLiveData<Long>()
    var finished = MutableLiveData<Boolean>()

    private val _seconds = MutableLiveData<Int>()
    fun seconds(): LiveData<Int> {
        return _seconds

    }

    fun startTimer() {
        timer = object : CountDownTimer(timerValue.value!! * 1000L, 1000) {
            override fun onFinish() {
                finished.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                _seconds.value = timeLeft.toInt()
//                timerValue.value = timeLeft
            }

        }.start()
    }

    fun pauseTimer() {
        timer?.cancel()
    }




}
