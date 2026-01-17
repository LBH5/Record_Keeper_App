package com.brnaime.recordkeeperapp.editrecord

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.brnaime.recordkeeperapp.databinding.ActivityEditRecordBinding
import java.io.Serializable


const val INTENT_EXTRA_SCREEN_DATA = "screen_data"

class EditRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRecordBinding
    private val screenData: ScreenData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA, ScreenData::class.java) as ScreenData
        else
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA) as ScreenData
    }
    private val recordPreferences by lazy { getSharedPreferences(screenData.sharePreferenceName, MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
        displayExistingRecord()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setupUi() {
        title = "${screenData.record} Record"
        binding.inputTextRecord.hint = screenData.recordFieldHint
        binding.buttonUpdateRecord.setOnClickListener {
            saveRunningRecords()
            finish()
        }
        binding.buttonClear.setOnClickListener {
            clearRunningRecords()
            finish()
        }
    }

    private fun clearRunningRecords() {
        recordPreferences.edit{
            remove("${screenData.record} record")
            remove("${screenData.record} date")
        }
    }

    private fun displayExistingRecord() {
        binding.editTextRecord.setText(recordPreferences.getString("${screenData.record} record", null))
        binding.editTextDate.setText(recordPreferences.getString("${screenData.record} date", null))
    }

    private fun saveRunningRecords() {
        recordPreferences.edit {
            putString("${this@EditRecordActivity.screenData.record} record", binding.editTextRecord.text.toString())
            putString("${this@EditRecordActivity.screenData.record} date", binding.editTextDate.text.toString())
        }
    }

    data class ScreenData(
        val record: String,
        val sharePreferenceName: String,
        val recordFieldHint:String
    ): Serializable

}