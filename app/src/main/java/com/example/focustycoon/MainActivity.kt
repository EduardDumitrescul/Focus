package com.example.focustycoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.focustycoon.storage.UserDataSource
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var userDataSource: UserDataSource

    @Inject
    lateinit var soundService: SoundService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MainApplication).appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        soundService.setActivityResumed()
    }

    override fun onPause() {
        super.onPause()
        userDataSource.saveData()
    }

    override fun onStop() {
        super.onStop()
        soundService.setActivityStopped()
    }
}