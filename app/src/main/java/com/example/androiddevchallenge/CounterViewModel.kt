package com.example.androiddevchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.CountDownTimer
import java.lang.Exception

class CounterViewModel: ViewModel() {

    private val _seconds = MutableLiveData<Int>()
    val seconds: LiveData<Int> = _seconds

    private val _timerFinished = MutableLiveData(true)
    val timerFinished: LiveData<Boolean> = _timerFinished

    private var countDownTimer: CountDownTimer? = null

    fun onStartClick() {
        countDownTimer = object : CountDownTimer(_seconds.value!!.toInt() * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _seconds.value = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                _timerFinished.value = true
            }
        }.start()
        _timerFinished.value = false
    }

    fun onSetTimer(input: String) {
        try {
            _seconds.value = input.toInt()
        } catch (exception: Exception) {
            _seconds.value = 0
        }
    }

    fun onPauseClick() {
        countDownTimer?.cancel()
    }
}