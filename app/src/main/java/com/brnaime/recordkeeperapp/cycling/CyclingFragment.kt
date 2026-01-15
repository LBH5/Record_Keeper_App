package com.brnaime.recordkeeperapp.cycling

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brnaime.recordkeeperapp.databinding.FragmentCyclingBinding
import com.brnaime.recordkeeperapp.editrecord.EditRecordActivity

class CyclingFragment: Fragment() {

    private lateinit var binding: FragmentCyclingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCyclingBinding.inflate(inflater,container,false)
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
        val cyclingPreferences = requireContext().getSharedPreferences("cycling", MODE_PRIVATE)
        binding.textViewLongestRideValue.text = cyclingPreferences.getString("Longest Ride record", null)
        binding.textViewLongestRideDate.text = cyclingPreferences.getString("Longest Ride date", null)
        binding.textViewBestClimbValue.text = cyclingPreferences.getString("Biggest Climb record", null)
        binding.textViewBestClimbDate.text = cyclingPreferences.getString("Biggest Climb date", null)
        binding.textViewBestAverageSpeedValue.text = cyclingPreferences.getString("Best Average Speed record", null)
        binding.textViewBestAverageSpeedDate.text = cyclingPreferences.getString("Best Average Speed date", null)
    }

    private fun setupClickListeners() {
        binding.containerLongestRide.setOnClickListener { launchCyclingRecordScreen("Longest Ride","Distance") }
        binding.containerBiggestClimb.setOnClickListener { launchCyclingRecordScreen("Biggest Climb","Height") }
        binding.containerBestAverageSpeed.setOnClickListener { launchCyclingRecordScreen("Best Average Speed","Avg Speed") }
    }

    private fun launchCyclingRecordScreen(ride:String,recordFieldHint:String) {
        val intent = Intent(this.context, EditRecordActivity::class.java)
        intent.putExtra("ScreenData", EditRecordActivity.ScreenData(ride,"cycling", recordFieldHint))
        startActivity(intent)
    }
}