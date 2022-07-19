package com.example.focustycoon.storage

import androidx.lifecycle.LiveData

interface UserRepository {
    fun getCurrentTokens(): LiveData<Float>
    fun addTokens(amount: Float)
    fun removeTokens(amount: Float)
    fun getMaxDuration(): LiveData<Int>
    fun getConversionRate(): LiveData<Float>
}