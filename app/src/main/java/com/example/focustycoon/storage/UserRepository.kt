package com.example.focustycoon.storage

import androidx.lifecycle.LiveData

interface UserRepository {
    fun getCurrentTokens(): LiveData<Double>
    fun addTokens(duration: Long)
    fun getConversionRate(): Double
    fun getConversionRate(level: Int): Double

    fun getEfficiencyLevel(): LiveData<Int>
    fun getEfficiencyUpgradeCost(): Double
    fun getEfficiencyUpgradeCost(level: Int): Double
    fun upgradeEfficiency(): Boolean

    fun getCapacityLevel(): LiveData<Int>
    fun getMaxCapacity(): Int
    fun getCapacityUpgradeCost(): Double
    fun getCapacityUpgradeCost(level: Int): Double
    fun upgradeCapacity(): Boolean
}