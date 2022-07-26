package com.example.focustycoon.focus.upgrade

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.focustycoon.storage.UserDataSource
import javax.inject.Inject

private const val TAG = "UpgradeViewModel"

class UpgradeViewModel @Inject constructor(private val userDataSource: UserDataSource): ViewModel() {
    var efficiencyLevelLiveData = userDataSource.getEfficiencyLevel()
    var capacityLevelLiveData = userDataSource.getCapacityLevel()

    fun getConversionRate() = userDataSource.getConversionRate()

    fun upgradeEfficiency() {
        Log.d(TAG, "upgradeEfficiency()")
        userDataSource.upgradeEfficiency()
    }

    fun upgradeCapacity() {
        userDataSource.upgradeCapacity()
    }
}