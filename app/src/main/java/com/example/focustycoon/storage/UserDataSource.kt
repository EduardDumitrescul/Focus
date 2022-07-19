package com.example.focustycoon.storage

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

private const val TOKEN_AMOUNT_KEY = "token_amount"
private const val TOKEN_CONVERSION_RATE_KEY = "token_conversion_rate"
private const val MAX_DURATION_KEY = "max_duration"

private const val TAG = "UserDataSource"

class UserDataSource @Inject constructor(var sharedPreferences: SharedPreferences): UserRepository {

    var tokenAmount: MutableLiveData<Float> = MutableLiveData(sharedPreferences.getFloat(TOKEN_AMOUNT_KEY, 0f))
    var tokenConversionRate: MutableLiveData<Float> = MutableLiveData(sharedPreferences.getFloat(TOKEN_CONVERSION_RATE_KEY, 1f))
    var maxDuration: MutableLiveData<Int> = MutableLiveData(sharedPreferences.getInt(MAX_DURATION_KEY, 12))


    override fun getCurrentTokens(): LiveData<Float> {
        return tokenAmount
    }

    override fun addTokens(amount: Float) {
        tokenAmount.value = tokenAmount.value?.plus(amount)
    }

    override fun removeTokens(amount: Float) {
        tokenAmount.value = tokenAmount.value?.minus(amount)
    }


    override fun getMaxDuration(): LiveData<Int> {
        return maxDuration
    }

    override fun getConversionRate(): LiveData<Float> {
        //for 5 minutes
        return tokenConversionRate
    }
}