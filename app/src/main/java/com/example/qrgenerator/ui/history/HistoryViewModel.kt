package com.example.qrgenerator.ui.history

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel: ViewModel() {
    val liveData = MutableLiveData<String>()

    init {
        startTimer()
        liveData.value = "777"
        Log.e("nurs", "VM is created")
    }

    private fun startTimer() {
        object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                liveData.value = (p0/1000).toString()
            }

            override fun onFinish() {
                liveData.value = "777"
            }
        }.start()
    }
}