package com.brnaime.recordkeeperapp


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.brnaime.recordkeeperapp.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar

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

        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_running -> onRunningBtnClicked()
                R.id.nav_cycling -> onCyclingBtnClicked()
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_content)
        when(currentFragment){
            is RunningFragment -> menuInflater.inflate(R.menu.toolbar_running, menu)
            is CyclingFragment -> menuInflater.inflate(R.menu.toolbar_cycling, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_content)
        return when (item.itemId){
            R.id.reset_running ->{
                if (currentFragment is RunningFragment) {
                    Toast.makeText(this, "Resetting running records", Toast.LENGTH_LONG).show()
                }
                true
            }
            R.id.reset_cycling ->{
                if (currentFragment is CyclingFragment) {
                    Toast.makeText(this, "Resetting cycling records", Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onRunningBtnClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment( ))
        }
        invalidateOptionsMenu()
        return true

    }
    private fun onCyclingBtnClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment( ))
        }
        invalidateOptionsMenu()
        return true
    }

}