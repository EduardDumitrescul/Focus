package com.example.focustycoon.focus

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.circularseekbar.CircularSeekBar
import com.example.focustycoon.MainApplication
import com.example.focustycoon.R
import com.example.focustycoon.databinding.FragmentFocusBinding
import com.example.focustycoon.focus.cancel_warning.ConfirmStopDialogFragment
import com.example.focustycoon.utils.StringConverterUtil
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(ConfirmStopDialogFragment.KEY) { _, bundle ->
            val confirm: Boolean = bundle.getBoolean(ConfirmStopDialogFragment.CONFIRM_KEY)
            if(confirm)
                stopTimer()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_focus, container, false)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.circularSeekBar.setOnChangeListener(this)
        viewModel.timeLeftLiveData.observe(viewLifecycleOwner) {
            setTimerValue(it)
        }
        viewModel.capacityLevelLiveData.observe(viewLifecycleOwner) {
            binding.circularSeekBar.setMaxValue(viewModel.getMaxCapacity() / 5)
        }
        viewModel.tokenAmount.observe(viewLifecycleOwner) {
            binding.tokenAmount.textString = StringConverterUtil.toString(it)
            Log.d(TAG, it.toString() + " " + StringConverterUtil.toString(it))
        }
    }

    private fun setTimerValue(value: Long) {
        Log.d(TAG, "setTimerValue() $value")

        val hours = value / 1000 / 3600
        val minutes = value / 1000 % 3600 / 60
        val seconds = value / 1000 % 60

        var string: String

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

    private var timer: CountDownTimer? = null
    private var cancelTimer: CountDownTimer? = null

    private var timerIsRunning: Boolean = false
    private var shouldShowDialog: Boolean = false

    fun startTimer() {
        if(timerIsRunning) {
            tryStoppingTimer()
            return
        }

        if(viewModel.timeLeftLiveData.value!! == 0L) {
            Toast.makeText(context, "Please select the duration first", Toast.LENGTH_LONG).show()
            return
        }

        timerIsRunning = true
        binding.button.text = resources.getString(R.string.cancel)
        showStartingMessage()

        cancelTimer?.cancel()
        shouldShowDialog = false
        cancelTimer = object: CountDownTimer(5000, 1000) {
            var current = 5
            override fun onTick(millisUntilFinished: Long) {
                binding.button.text = resources.getString(R.string.cancel_with_timer, current)
                current --
            }

            override fun onFinish() {
                binding.button.text = resources.getString(R.string.cancel)
                shouldShowDialog = true
                showWorkingMessage()
            }

        }.start()

        assert(binding.viewmodel != null)
        assert(binding.viewmodel!!.timeLeftLiveData.value != null)

        viewModel.startTime = System.currentTimeMillis()
        viewModel.duration = viewModel.timeLeftLiveData.value!! / 300000
        Log.d(TAG, "startTimer() ${viewModel.duration}")
        binding.circularSeekBar.setIsLocked(CircularSeekBar.MODE_LOCKED)
        binding.circularSeekBar.setIsThumbVisible(false)

        Log.d(TAG, "timerStart()")

        timer?.cancel()
        /** modify duration multiplier (should pe 300 * 1000) **/
        timer = object: CountDownTimer(binding.viewmodel!!.duration * 3000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.viewmodel!!.updateTime()
                viewModel.timeLeftLiveData.value?.let {
                    binding.circularSeekBar.setValue(it.toFloat() / 1000 / 300)
                }
            }

            override fun onFinish() {
                binding.circularSeekBar.setIsLocked(CircularSeekBar.MODE_UNLOCKED)
                binding.circularSeekBar.setIsThumbVisible(true)

                val earned = viewModel.taskFinished()
                showFinishedMessage(earned)
                finishedTimer()
            }
        }.start()
    }

    private fun tryStoppingTimer() {
        if(!shouldShowDialog) {
            cancelTimer?.cancel()
            stopTimer()
        }
        else {
            findNavController().navigate(R.id.openConfirmStopDialog)
        }
    }

    private fun stopTimer() {
        Log.d(TAG, "stopTimer()")
        timer?.cancel()
        showCancelMessage()
        finishedTimer()
    }

    private fun finishedTimer() {
        binding.circularSeekBar.setIsLocked(CircularSeekBar.MODE_UNLOCKED)
        binding.circularSeekBar.setIsThumbVisible(true)
        timerIsRunning = false
        binding.button.text = resources.getString(R.string.focus)

    }

    private fun showCancelMessage() {
        binding.messageTextView.text = resources.getString(R.string.cancel_message)
    }

    private fun showFinishedMessage(earned: Long) {
        binding.messageTextView.text = resources.getString(R.string.earning_message, earned)
    }

    private fun showWorkingMessage() {
        binding.messageTextView.text = resources.getString(R.string.working_message)
    }

    private fun showStartingMessage() {
        binding.messageTextView.text = resources.getString(R.string.starting_work_message)
    }

    fun openUpgradeDialog() {
        findNavController().navigate(R.id.openUpgradeDialog)
    }

    override fun onValueChangeDetected(value: Int) {
        viewModel.setTimeLeft(300L * 1000L * value)
    }
}