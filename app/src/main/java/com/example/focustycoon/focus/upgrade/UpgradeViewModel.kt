package com.example.focustycoon.focus.upgrade

import androidx.lifecycle.ViewModel
import com.example.focustycoon.storage.UserDataSource
import javax.inject.Inject

class UpgradeViewModel @Inject constructor(private val userDataSource: UserDataSource): ViewModel() {
    var efficiencyLevelLiveData = userDataSource.getEfficiencyLevel()
    var capacityLevelLiveData = userDataSource.getCapacityLevel()
    var tokenAmountLiveData = userDataSource.getCurrentTokens()

    fun getConversionRate() = userDataSource.getConversionRate()

    fun getMaxCapacity() = userDataSource.getMaxCapacity()

    fun getEfficiencyUpgradeCost() = userDataSource.getEfficiencyUpgradeCost()
    fun getCapacityUpgradeCost() = userDataSource.getCapacityUpgradeCost()

    fun upgradeEfficiency(): Boolean {
        return userDataSource.upgradeEfficiency()
    }

    fun upgradeCapacity(): Boolean {
        return userDataSource.upgradeCapacity()
    }

    fun getMaxCapacityLevel() = UserDataSource.MAX_CAPACITY_LEVEL
}