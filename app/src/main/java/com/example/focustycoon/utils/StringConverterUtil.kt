package com.example.focustycoon.utils

import android.util.Log
import kotlin.math.round

private const val TAG = "StringConverterUtil"

class StringConverterUtil {
    companion object {
        fun toString(value: Float): String {
            val vDouble: Double = round(value.toDouble())
            if((vDouble * 10).toLong() % 10 != 0L && vDouble < 1000) {
                return vDouble.toString()
            }

            return toString(value.toLong())
        }

        fun toString(value: Int) = toString(value.toLong())

        fun toString(value: Long): String {
            val billion = 1000000000L
            val million = 1000000L
            val thousand = 1000L
            return when {
                value >= billion -> {
                    val x = value.toDouble() / billion
                    String.format("%.2f", x) + "b"
                }
                value >= million -> {
                    val x = value.toDouble() / million
                    String.format("%.2f", x) + "m"
                }
                value >= thousand -> {
                    val x = value.toDouble() / thousand
                    String.format("%.2f", x) + "k"
                }
                else -> {
                    value.toString()
                }
            }
        }
    }
}