package com.example.focustycoon.focus.upgrade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.focustycoon.R
import com.example.focustycoon.databinding.DialogUpgradesBinding

class UpgradeDialogFragment: DialogFragment() {

    private lateinit var binding: DialogUpgradesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_upgrades, container, false)
        return binding.root
    }
}