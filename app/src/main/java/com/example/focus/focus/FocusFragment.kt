package com.example.focus.focus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.circularseekbar.CircularSeekBar
import com.example.focus.MainApplication
import com.example.focus.R
import com.example.focus.SoundService
import com.example.focus.databinding.FragmentFocusBinding
import com.example.focus.focus.cancel_warning.ConfirmStopDialogFragment
import com.example.focus.notification.AlarmManagerUtil
import com.example.focus.settings.GlobalSettings
import com.example.focus.settings.SettingsActivity
import com.example.focus.utils.StringConverterUtil
import javax.inject.Inject

class FocusFragment: Fragment(), CircularSeekBar.OnChangeListener {
    private lateinit var binding: FragmentFocusBinding
    @Inject lateinit var soundService: SoundService

    @Inject
    lateinit var viewModel: FocusViewModel
    @Inject
    lateinit var alarmManagerUtil: AlarmManagerUtil
    @Inject
    lateinit var globalSettings: GlobalSettings

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

    fun startSettingsActivity() {
        val intent = Intent(context, SettingsActivity::class.java)
        startActivity(intent)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openConfirmPrivacyDialog()
        binding.circularSeekBar.setOnChangeListener(this)
        viewModel.timeLeftLiveData.observe(viewLifecycleOwner) {
            setTimerValue(it)
        }
        viewModel.capacityLevelLiveData.observe(viewLifecycleOwner) {
            binding.circularSeekBar.setMaxValue(viewModel.getMaxCapacity() / 5)
        }
        viewModel.tokenAmount.observe(viewLifecycleOwner) {
            binding.tokenAmount.textString = StringConverterUtil.toString(it)
        }

        // Check whether the timer is already running
        if(viewModel.startTime + viewModel.duration > System.currentTimeMillis()) {
            viewModel.setTimeLeft(viewModel.startTime + viewModel.duration  - System.currentTimeMillis())
            setupNormalTimer()
        }
        else if(viewModel.duration > 0) {
            getReward()
            finishedTimer()
        }



    }


    private fun openConfirmPrivacyDialog() {
        if(globalSettings.policyConfirmed) {
            return
        }
        findNavController().navigate(R.id.openPolicyConfirmDialog)
    }

    private fun setTimerValue(value: Long) {

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

    fun startSession() {
        if(timerIsRunning) {
            tryStoppingTimer()
            return
        }

        if(viewModel.timeLeftLiveData.value!! == 0L) {
            Toast.makeText(context, "Please select the duration first", Toast.LENGTH_LONG).show()
            return
        }

        viewModel.startTime = System.currentTimeMillis()



        // keep this order
        setupNormalTimer()
        setupCancelTimer()



    }

    private fun setupNormalTimer() {
        timerIsRunning = true
        shouldShowDialog = true
        binding.button.text = resources.getString(R.string.cancel)
        showStartingMessage()


        assert(binding.viewmodel != null)
        assert(binding.viewmodel!!.timeLeftLiveData.value != null)

        binding.circularSeekBar.setIsLocked(CircularSeekBar.MODE_LOCKED)
        binding.circularSeekBar.setIsThumbVisible(false)


        timer?.cancel()
        alarmManagerUtil.sendFinishedSessionNotification(viewModel.timeLeftLiveData.value!!)

        timer = object: CountDownTimer(viewModel.timeLeftLiveData.value!!, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.viewmodel!!.updateTime()
                viewModel.timeLeftLiveData.value?.let {
                    binding.circularSeekBar.setValue(it.toFloat() / 1000 / 300)
                }
            }

            override fun onFinish() {
                getReward()
                finishedTimer()
            }
        }.start()
    }

    private fun setupCancelTimer() {
        cancelTimer?.cancel()
        shouldShowDialog = false
        cancelTimer = object: CountDownTimer(5000, 1000) {
            var current = 5
            override fun onTick(millisUntilFinished: Long) {
                soundService.playTimerTick1()
                binding.button.text = resources.getString(R.string.cancel_with_timer, current)
                current --
            }

            override fun onFinish() {
                binding.button.text = resources.getString(R.string.cancel)
                soundService.playTimerTick2()
                shouldShowDialog = true
                showWorkingMessage()
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
        timer?.cancel()
        showCancelMessage()
        finishedTimer()
    }

    fun getReward() {
        val earned = viewModel.taskFinished()
        soundService.playSuccessSound()
        showFinishedMessage(earned)
    }

    private fun finishedTimer() {
        binding.circularSeekBar.setIsLocked(CircularSeekBar.MODE_UNLOCKED)
        binding.circularSeekBar.setIsThumbVisible(true)
        binding.circularSeekBar.setValue(0)
        viewModel.reset()
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

    override fun onPause() {
        super.onPause()
        viewModel.saveState()
    }

    override fun onValueChangeDetected(value: Int) {
        viewModel.duration = 300L * 1000L * value
    }
}