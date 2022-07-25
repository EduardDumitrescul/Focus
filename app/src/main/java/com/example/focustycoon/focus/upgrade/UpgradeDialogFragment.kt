package com.example.focustycoon.focus.upgrade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        setupEfficiencyUpgrade()
        setupDurationUpgrade()
        return binding.root
    }

    private fun setupEfficiencyUpgrade() {
        val upgrade = binding.upgradeEfficiency
        val nameTextView: TextView = upgrade.findViewById(R.id.nameTextView)
        val levelTextView: TextView = upgrade.findViewById(R.id.levelTextView)
        val infoTextView: TextView = upgrade.findViewById(R.id.infoTextView)

        nameTextView.text = "EFFICIENCY"
        levelTextView.text = (-1).toString()
        infoTextView.text = "Currently, you gain -1 tokens for every -1 minuts of focused time"
    }

    private fun setupDurationUpgrade() {
        val upgrade = binding.upgradeDuration
        val nameTextView: TextView = upgrade.findViewById(R.id.nameTextView)
        val levelTextView: TextView = upgrade.findViewById(R.id.levelTextView)
        val infoTextView: TextView = upgrade.findViewById(R.id.infoTextView)

        nameTextView.text = "Capacity"
        levelTextView.text = (-1).toString()
        infoTextView.text = "The maximum timer duration is -1 minutes"
    }
}