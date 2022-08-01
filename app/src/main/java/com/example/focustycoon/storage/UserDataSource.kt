package com.example.focustycoon.storage

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.round

private const val TOKEN_AMOUNT_KEY = "token_amount"
private const val EFFICIENCY_LEVEL_KEY = "efficiency_level"
private const val DURATION_LEVEL_KEY = "duration_level"

private const val TAG = "UserDataSource"
private const val BASE_COST: Int = 10

class UserDataSource @Inject constructor(var sharedPreferences: SharedPreferences): UserRepository {

    companion object {
        /** The smallest unit of time **/
        const val TIME_UNIT: Int = 5
        const val MAX_CAPACITY_LEVEL: Int = 11
    }

    var tokenAmount: MutableLiveData<Long> = MutableLiveData(sharedPreferences.getLong(TOKEN_AMOUNT_KEY, 199999990L))
    var efficiencyLevel: MutableLiveData<Int> = MutableLiveData(sharedPreferences.getInt(EFFICIENCY_LEVEL_KEY, 1))
    var capacityLevel: MutableLiveData<Int> = MutableLiveData(sharedPreferences.getInt(DURATION_LEVEL_KEY, 1))


    override fun getCurrentTokens(): LiveData<Long> {
        return tokenAmount
    }

    override fun addTokens(duration: Long) {
        tokenAmount.value = tokenAmount.value?.plus(duration * getConversionRate())
    }


    override fun getMaxCapacity(): Int {
        assert(capacityLevel.value != null)
        return when(capacityLevel.value!!) {
            1 -> 60
            2 -> 80
            3 -> 100
            4 -> 120
            5 -> 150
            6 -> 180
            7 -> 240
            8 -> 300
            9 -> 360
            10 -> 420
            11 -> 480
            else -> -1
        }
    }

    /** Returns the conversion rate of 5 minutes **/
    override fun getConversionRate(): Long {
        //for 5 minutes
        assert(efficiencyLevel.value != null)
        val level: Int = efficiencyLevel.value!!
        val rate: Long = (2.0.pow((level - 1) / 2) * (5 + 2 * ((level - 1) % 2))).toLong()
        return rate
    }

    /** Returns the conversion rate of 5 minutes **/
    override fun getConversionRate(level: Int): Long {
        val rate: Long = (2.0.pow((level - 1) / 2) * (5 + 2 * ((level - 1) % 2))).toLong()
        return rate
    }

    override fun getEfficiencyUpgradeCost(): Long {
        assert(efficiencyLevel.value != null)
        val level: Int = efficiencyLevel.value!!
        var coef = 5L
        if((level - 1) % 3 == 1) {
            coef += 2L
        }
        else if((level - 1) % 3 == 2) {
            coef += 5L
        }
        val price: Long = (3.0.pow((level - 1) / 3) * coef).toLong()
        return BASE_COST * price
    }

    override fun getEfficiencyUpgradeCost(level: Int): Long {
        var coef = 5L
        if((level - 1) % 3 == 1) {
            coef += 2L
        }
        else if((level - 1) % 3 == 2) {
            coef += 5L
        }
        val price: Long = (3.0.pow((level - 1) / 3) * coef).toLong()
        return BASE_COST * price
    }

    override fun getEfficiencyLevel(): LiveData<Int> {
        return efficiencyLevel
    }

    /** Returns true if successful, false otherwise */
    override fun upgradeEfficiency(): Boolean {
        assert(tokenAmount.value != null)
        if(tokenAmount.value!! < getEfficiencyUpgradeCost()){
            return false
        }
        tokenAmount.value = tokenAmount.value!! - getEfficiencyUpgradeCost()
        efficiencyLevel.value = efficiencyLevel.value!! + 1
        Log.d(TAG, "upgradeEfficiency()")
        return true
    }

    override fun getCapacityLevel(): LiveData<Int> {
        return capacityLevel
    }

    override fun getCapacityUpgradeCost(): Long {
        assert(capacityLevel.value != null)
        val level = capacityLevel.value!!
        return (2.0.pow(level + 1) * getMaxCapacity()).toLong()
    }

    override fun getCapacityUpgradeCost(level: Int): Long {
        return (2.0.pow(level + 1) * getMaxCapacity()).toLong()
    }

    /** Returns true if successful, false otherwise */
    override fun upgradeCapacity(): Boolean {
        assert(tokenAmount.value != null)
        if(tokenAmount.value!! < getCapacityUpgradeCost()){
            return false
        }
        if(capacityLevel.value == 11) {
            return false
        }
        tokenAmount.value = tokenAmount.value!! - getCapacityUpgradeCost()
        capacityLevel.value = capacityLevel.value!! + 1
        return true
    }
}