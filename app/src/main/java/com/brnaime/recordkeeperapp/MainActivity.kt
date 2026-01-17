package com.brnaime.recordkeeperapp


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.brnaime.recordkeeperapp.cycling.CyclingFragment
import com.brnaime.recordkeeperapp.databinding.ActivityMainBinding
import com.brnaime.recordkeeperapp.running.RunningFragment
import com.google.android.material.snackbar.Snackbar
import java.util.Locale


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
                    showConfirmationAlert(RUNNING_DISPLAY_VALUE)
                }
                true
            }
            R.id.reset_cycling ->{
                if (currentFragment is CyclingFragment) {
                    showConfirmationAlert(CYCLING_DISPLAY_VALUE)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmationAlert(selection: String) {
        AlertDialog.Builder(this)
            .setTitle("Resetting $selection records")
            .setMessage("Are you sure you want to delete all $selection records?")
            .setPositiveButton("Yes") { _, _ ->
                when(selection){
                    RUNNING_DISPLAY_VALUE -> getSharedPreferences(RunningFragment.PR_FILENAME, MODE_PRIVATE).edit { clear() }
                    CYCLING_DISPLAY_VALUE -> getSharedPreferences(CyclingFragment.PR_FILENAME, MODE_PRIVATE).edit { clear() }
                }
                refreshFragments(selection)
                showConfirmation(selection)


            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun refreshFragments(selection: String) {
        when (selection) {
            RunningFragment.PR_FILENAME -> onRunningBtnClicked()
            CyclingFragment.PR_FILENAME -> onCyclingBtnClicked()
        }
    }

    private fun showConfirmation(selection: String) {
        val snackBar = Snackbar.make(
            binding.frameContent,
            "${
                selection.replaceFirstChar {
                    if (it.isLowerCase())
                        it.titlecase(Locale.getDefault())
                    else it.toString()
                }
            } records cleared successfully!",
            Snackbar.LENGTH_LONG)
        snackBar.anchorView = binding.bottomNav
        snackBar.show()
    }

    private fun onRunningBtnClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment())
        }
        invalidateOptionsMenu()
        return true

    }
    private fun onCyclingBtnClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment())
        }
        invalidateOptionsMenu()
        return true
    }

    companion object {
        const val  RUNNING_DISPLAY_VALUE = "running"
        const val CYCLING_DISPLAY_VALUE = "cycling"
    }

}
