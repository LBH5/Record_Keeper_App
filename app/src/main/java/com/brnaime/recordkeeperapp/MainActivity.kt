package com.brnaime.recordkeeperapp


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.brnaime.recordkeeperapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bottomNav?.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_running ->{
                    onRunningBtnClicked()
                    true
                }
                R.id.nav_cycling ->{
                    onCyclingBtnClicked()
                    true
                }
                else -> false
            }
        }
    }

    private fun onRunningBtnClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment( ))
        }

    }
    private fun onCyclingBtnClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment( ))
        }

    }

}