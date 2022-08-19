package com.example.focustycoon.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

private const val SOUND_ENABLED_KEY = "sound_enabled"

class GlobalSettings @Inject constructor(private val sharedPreferences: SharedPreferences) {
    var soundEnabled: Boolean = sharedPreferences.getBoolean(SOUND_ENABLED_KEY, true)
    set(value) {
        field = value
        sharedPreferences.edit {
            putBoolean(SOUND_ENABLED_KEY, soundEnabled)
        }
    }
}