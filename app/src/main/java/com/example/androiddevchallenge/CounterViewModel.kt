/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception

class CounterViewModel : ViewModel() {

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
