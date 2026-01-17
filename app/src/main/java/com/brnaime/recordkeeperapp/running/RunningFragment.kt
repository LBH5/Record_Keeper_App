package com.brnaime.recordkeeperapp.running

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brnaime.recordkeeperapp.databinding.FragmentRunningBinding
import com.brnaime.recordkeeperapp.editrecord.EditRecordActivity
import com.brnaime.recordkeeperapp.editrecord.INTENT_EXTRA_SCREEN_DATA

class RunningFragment: Fragment() {

    private lateinit var binding: FragmentRunningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunningBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun displayRecords() {
        val runningPreferences = requireContext().getSharedPreferences(PR_FILENAME, MODE_PRIVATE)
        binding.textView5kmValue.text = runningPreferences.getString("5km record", null)
        binding.textView10kmValue.text = runningPreferences.getString("10km record", null)
        binding.textViewHalfMarathonValue.text = runningPreferences.getString("Half Marathon record", null)
        binding.textViewMarathonValue.text = runningPreferences.getString("Marathon record", null)
        binding.textView5kmDate.text = runningPreferences.getString("5km date", null)
        binding.textView10kmDate.text = runningPreferences.getString("10km date", null)
        binding.textViewHalfMarathonDate.text = runningPreferences.getString("Half Marathon date", null)
        binding.textViewMarathonDate.text = runningPreferences.getString("Marathon date", null)
    }

    private fun setupClickListeners(){
        binding.container5km.setOnClickListener { launchRunningRecordScreen("5km") }
        binding.container10km.setOnClickListener { launchRunningRecordScreen("10km") }
        binding.containerHalfMarathon.setOnClickListener { launchRunningRecordScreen("Half Marathon") }
        binding.containerMarathon.setOnClickListener { launchRunningRecordScreen("Marathon") }
    }

    private fun launchRunningRecordScreen(distance: String) {
        val intent = Intent(this.context, EditRecordActivity::class.java)
        intent.putExtra(INTENT_EXTRA_SCREEN_DATA, EditRecordActivity.ScreenData(distance,
            PR_FILENAME, "Time"))
        startActivity(intent)
    }

    companion object {
        const val PR_FILENAME = "running"
    }
}


