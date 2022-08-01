package com.example.focustycoon.focus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.focustycoon.storage.UserDataSource
import javax.inject.Inject

private const val TAG = "FocusViewModel"

class FocusViewModel @Inject constructor(private val userDataSource: UserDataSource): ViewModel() {

    var tokenAmount = userDataSource.getCurrentTokens()
    val capacityLevelLiveData = userDataSource.getCapacityLevel()

    fun getMaxCapacity() = userDataSource.getMaxCapacity()

    fun taskFinished() {
        userDataSource.addTokens(duration)
    }

    /** in milliseconds **/
    var startTime: Long = 0

    /** 1 unit = 5 minutes **/
    var duration: Long = 0

    /** in milliseconds **/
    val timeLeftLiveData: MutableLiveData<Long> = MutableLiveData(0)



    fun setTimeLeft(timeLeft: Long) {
        Log.d(TAG, "setTimeLeft() $timeLeft")
        timeLeftLiveData.value = timeLeft
    }

    fun updateTime() {
        Log.d(TAG, "$startTime $duration ${System.currentTimeMillis()}")
        timeLeftLiveData.value = startTime + duration * 1000 * 300 - System.currentTimeMillis()
    }
}