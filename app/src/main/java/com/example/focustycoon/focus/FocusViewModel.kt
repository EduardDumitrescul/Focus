package com.example.focustycoon.focus

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.focustycoon.storage.UserDataSource
import javax.inject.Inject

private const val TAG = "FocusViewModel"

class FocusViewModel @Inject constructor(private val userDataSource: UserDataSource): ViewModel() {

    var tokenAmount = userDataSource.getCurrentTokens()
    val capacityLevelLiveData = userDataSource.getCapacityLevel()

    fun getMaxCapacity() = userDataSource.getMaxCapacity()

    fun taskFinished(): Long {
        return userDataSource.addTokens(duration)
    }

    /** in milliseconds **/
    var startTime: Long = userDataSource.getStartTime()
        set(value) {
            field = value
            userDataSource.setStartTime(value)
        }

    /** millis **/
    var duration: Long = userDataSource.getCurrentDuration()
        set(value) {
            field = value
            setTimeLeft(duration)
        }

    /** in milliseconds **/
    val timeLeftLiveData: MutableLiveData<Long> = MutableLiveData(0)

    fun reset() {
        duration = 0
        startTime = 0
        setTimeLeft(0)
    }

    fun saveState() {
        userDataSource.setStartTime(startTime)
        userDataSource.setCurrentDuration(duration)
    }

    fun setTimeLeft(timeLeft: Long) {
        timeLeftLiveData.value = timeLeft
    }

    fun updateTime() {
        timeLeftLiveData.value = startTime + duration - System.currentTimeMillis()
    }
}