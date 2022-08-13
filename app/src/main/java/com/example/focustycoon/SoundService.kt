package com.example.focustycoon

import android.content.Context
import android.media.MediaPlayer
import javax.inject.Inject

class SoundService @Inject constructor(private val context: Context){
    private var mediaPlayer: MediaPlayer? = null

    fun playTimerTick1() {
        mediaPlayer = MediaPlayer.create(context, R.raw.beep1)
        mediaPlayer?.start()
    }

    fun playTimerTick2() {
        mediaPlayer = MediaPlayer.create(context, R.raw.beep2)
        mediaPlayer?.start()
    }
}