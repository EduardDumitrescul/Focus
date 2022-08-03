package com.example.focustycoon.focus.upgrade

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.focustycoon.MainApplication
import com.example.focustycoon.R
import com.example.focustycoon.databinding.DialogUpgradesBinding
import com.example.focustycoon.storage.UserDataSource
import com.example.focustycoon.utils.StringConverterUtil
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
            updateEfficiencyUpgrade()
        }
        viewModel.capacityLevelLiveData.observe(viewLifecycleOwner) {
            updateCapacityUpgrade()
        }
        binding.upgradeEfficiency.upgradeButton.setOnClickListener {
            val success = viewModel.upgradeEfficiency()
            if(!success) {
                showNotEnoughTokensToast()
            }
        }
        binding.upgradeCapacity.upgradeButton.setOnClickListener {
            val success = viewModel.upgradeCapacity()
            if(!success) {
                showNotEnoughTokensToast()
            }
        }
        viewModel.tokenAmountLiveData.observe(viewLifecycleOwner) {
            binding.tokenAmount.textString = StringConverterUtil.toString(it)
        }
    }

    private fun showNotEnoughTokensToast() {
        Toast.makeText(this.context, "You do not have enough Focus Tokens", Toast.LENGTH_LONG).show()
    }

    private fun updateEfficiencyUpgrade() {
        val upgrade = binding.upgradeEfficiency

        upgrade.nameTextView.text = getString(R.string.efficiency)
        viewModel.efficiencyLevelLiveData.value?.let {
            upgrade.levelTextView.text = resources.getString(R.string.level_count, it)
        }
        val stringConvRate = StringConverterUtil.toString(viewModel.getConversionRate())
        upgrade.infoTextView.text = resources.getString(R.string.token_gain, stringConvRate, UserDataSource.TIME_UNIT)
        upgrade.imageView.setImageResource(R.drawable.outline_insights_24)
        val stringCost = StringConverterUtil.toString(viewModel.getEfficiencyUpgradeCost())
        upgrade.upgradeButton.text = stringCost
    }

    private fun updateCapacityUpgrade() {
        val upgrade = binding.upgradeCapacity

        upgrade.nameTextView.text = getString(R.string.capacity)
        viewModel.capacityLevelLiveData.value?.let {
            upgrade.levelTextView.text = resources.getString(R.string.level_count, it)
        }

        upgrade.infoTextView.text = resources.getString(R.string.max_capacity, viewModel.getMaxCapacity())
        upgrade.imageView.setImageResource(R.drawable.outline_psychology_24)
        if(viewModel.capacityLevelLiveData.value == viewModel.getMaxCapacityLevel()) {
            upgrade.upgradeButton.text = resources.getString(R.string.max)
        }
        else {
            val stringCost = StringConverterUtil.toString(viewModel.getCapacityUpgradeCost())
            upgrade.upgradeButton.text = stringCost
        }
    }
}