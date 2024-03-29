package com.example.focus

import android.content.Context
import android.media.MediaPlayer
import com.example.focus.settings.GlobalSettings
import javax.inject.Inject

class SoundService @Inject constructor(private val context: Context, private val globalSettings: GlobalSettings){
    private var mediaPlayer: MediaPlayer? = null

    fun playTimerTick1() {
        if(!MainApplication.isActive || !globalSettings.soundEnabled) {
             return
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.beep1)
        mediaPlayer?.start()
    }

    fun playTimerTick2() {
        if(!MainApplication.isActive || !globalSettings.soundEnabled) {
            return
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.beep2)
        mediaPlayer?.start()
    }

    fun playSuccessSound() {
        if(!MainApplication.isActive || !globalSettings.soundEnabled) {
            return
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.finish_success)
        mediaPlayer?.start()
    }
}