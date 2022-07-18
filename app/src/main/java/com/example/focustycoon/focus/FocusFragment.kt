package com.example.focustycoon.focus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.circularseekbar.CircularSeekBar
import com.example.focustycoon.MainApplication
import com.example.focustycoon.R
import com.example.focustycoon.databinding.FragmentFocusBinding
import javax.inject.Inject

private const val TAG = "FocusFragment"

class FocusFragment: Fragment(), CircularSeekBar.OnChangeListener {
    private lateinit var binding: FragmentFocusBinding

    @Inject
    lateinit var viewModel: FocusViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_focus, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.circularSeekBar.setOnChangeListener(this)
        binding.viewmodel?.timeLeftLiveData?.observe(viewLifecycleOwner) {
            setTimerValue(it.toInt())
        }
    }

    private fun setTimerValue(value: Int) {
        val hours: Int = value / 3600
        val minutes: Int = value % 3600 / 60
        val seconds: Int = value % 60

        var string: String = ""

        if(hours > 0) {
            string = hours.toString() + "h "
            string += if (minutes < 10) "0${minutes}m" else minutes.toString() + "m"
        }
        else {
            string = minutes.toString() + "m "
            string += if (seconds < 10) "0${seconds}s" else seconds.toString() + "s"
        }
        binding.textView.text = string
    }

    override fun onValueChangeDetected(value: Int) {
        viewModel.setTimeLeft(60 * value)
    }
}