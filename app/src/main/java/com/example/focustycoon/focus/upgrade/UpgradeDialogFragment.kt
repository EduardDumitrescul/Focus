package com.example.focustycoon.focus.upgrade

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.focustycoon.MainApplication
import com.example.focustycoon.R
import com.example.focustycoon.databinding.DialogUpgradesBinding
import com.example.focustycoon.storage.UserDataSource
import javax.inject.Inject

private const val TAG = "UpgradeDialogFragment"

class UpgradeDialogFragment: DialogFragment() {

    @Inject
    lateinit var viewModel: UpgradeViewModel

    private lateinit var binding: DialogUpgradesBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_upgrades, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        updateEfficiencyUpgrade()
        updateCapacityUpgrade()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.efficiencyLevelLiveData.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated()")
            updateEfficiencyUpgrade()
        }
        viewModel.capacityLevelLiveData.observe(viewLifecycleOwner) {
            updateCapacityUpgrade()
        }
        binding.upgradeEfficiency.upgradeButton.setOnClickListener {
            viewModel.upgradeEfficiency()
        }
        binding.upgradeCapacity.upgradeButton.setOnClickListener {
            viewModel.upgradeCapacity()
        }
        viewModel.tokenAmountLiveData.observe(viewLifecycleOwner) {
            binding.tokenAmount.textView.text = it.toString()
        }
    }

    private fun updateEfficiencyUpgrade() {
        val upgrade = binding.upgradeEfficiency

        upgrade.nameTextView.text = "EFFICIENCY"
        viewModel.efficiencyLevelLiveData.value?.let {
            upgrade.levelTextView.text = "level $it"
        }
        upgrade.infoTextView.text = "Currently, you gain ${viewModel.getConversionRate()} tokens for every ${UserDataSource.TIME_UNIT} minutes of focused time"
        upgrade.imageView.setImageResource(R.drawable.outline_insights_24)
        upgrade.upgradeButton.text = "${viewModel.getEfficiencyUpgradeCost()}"
    }

    private fun updateCapacityUpgrade() {
        val upgrade = binding.upgradeCapacity

        upgrade.nameTextView.text = "Capacity"
        viewModel.capacityLevelLiveData.value?.let {
            upgrade.levelTextView.text = "level $it"
        }

        upgrade.infoTextView.text = "The maximum timer duration is ${viewModel.getMaxCapacity()} minutes"
        upgrade.imageView.setImageResource(R.drawable.outline_psychology_24)
        upgrade.upgradeButton.text = "${viewModel.getCapacityUpgradeCost()}"
    }
}