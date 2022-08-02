package com.example.focustycoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.focustycoon.storage.UserDataSource
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var userDataSource: UserDataSource


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MainApplication).appComponent.inject(this)
    }

    override fun onPause() {
        super.onPause()
        userDataSource.saveData()
    }
}