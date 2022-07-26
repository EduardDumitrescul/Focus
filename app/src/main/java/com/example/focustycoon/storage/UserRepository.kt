package com.example.focustycoon.storage

import androidx.lifecycle.LiveData

interface UserRepository {
    fun getCurrentTokens(): LiveData<Float>
    fun addTokens(duration: Long)
    fun getMaxDuration(): LiveData<Int>
    fun getConversionRate(): Float
    fun getConversionRate(level: Int): Float

    fun getEfficiencyLevel(): LiveData<Int>
    fun getEfficiencyUpgradeCost(): Float
    fun getEfficiencyUpgradeCost(level: Int): Float
    fun upgradeEfficiency(): Boolean

    fun getCapacityLevel(): LiveData<Int>
//    fun getCapacityUpgradeCost(): Float
//    fun getCapacityUpgradeCost(level: Int): Float
    fun upgradeCapacity(): Boolean
}