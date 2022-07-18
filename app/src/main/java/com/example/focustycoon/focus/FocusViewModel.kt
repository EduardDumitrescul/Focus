package com.example.focustycoon.focus

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

private const val TAG = "FocusViewModel"

class FocusViewModel @Inject constructor(): ViewModel() {
    var startTimeLiveData: MutableLiveData<Long> = MutableLiveData(0)
    var durationLiveData: MutableLiveData<Long> = MutableLiveData(0)

    //in seconds
    val timeLeftLiveData: MutableLiveData<Int> = MutableLiveData(0)

    fun setTimeLeft(timeLeft: Int) {
        Log.d(TAG, "setTimeLeft() $timeLeft")
        timeLeftLiveData.value = timeLeft
    }
}