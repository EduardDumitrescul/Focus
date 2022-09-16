@file:Suppress("unused")

package com.example.focustycoon.utils

class StringConverterUtil {
    companion object {
        fun toString(value: Double): String {
            if((value * 10).toLong() % 10 != 0L && value < 1000) {
                return value.toString()
            }


            return toString(value.toLong())
        }

        fun toString(value: Long): String {
            val billion = 1000000000L
            val million = 1000000L
            val thousand = 1000L
            return when {
                value >= billion -> {
                    val x = value * 100 / billion / 100.0
                    String.format("%.2f", x) + "b"
                }
                value >= million -> {
                    val x = value * 100 / million / 100.0
                    String.format("%.2f", x) + "m"
                }
                value >= thousand -> {
                    val x = value * 100 / thousand / 100.0
                    String.format("%.2f", x) + "k"
                }
                else -> {
                    value.toString()
                }
            }
        }
    }
}