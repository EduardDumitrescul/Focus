package com.example.focustycoon.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

private const val SOUND_ENABLED_KEY = "sound_enabled"
private const val NOTIFICATIONS_ENABLED_KEY = "notifications_enabled"
private const val POLICY_CONFIRM_KEY = "policy_confirm"

class GlobalSettings @Inject constructor(private val sharedPreferences: SharedPreferences) {
    var soundEnabled: Boolean = sharedPreferences.getBoolean(SOUND_ENABLED_KEY, true)
    set(value) {
        field = value
        sharedPreferences.edit {
            putBoolean(SOUND_ENABLED_KEY, value)
        }
    }

    var notificationsEnabled: Boolean = sharedPreferences.getBoolean(NOTIFICATIONS_ENABLED_KEY, true)
    set(value) {
        field = value
        sharedPreferences.edit {
            putBoolean(NOTIFICATIONS_ENABLED_KEY, value)
        }
    }

    var policyConfirmed: Boolean = sharedPreferences.getBoolean(POLICY_CONFIRM_KEY, false)
    set(value) {
        field = value
        sharedPreferences.edit {
            putBoolean(POLICY_CONFIRM_KEY, value)
        }
    }
}