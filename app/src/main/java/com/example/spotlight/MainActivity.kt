package com.example.spotlight

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.spotlight.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.spotlight.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_spotlight_logo)

        binding.toolbar.post {
            for (i in 0 until binding.toolbar.childCount) {
                val child = binding.toolbar.getChildAt(i)
                if (child is ImageView) {
                    val sizePx = (36 * resources.displayMetrics.density).toInt()
                    child.layoutParams = child.layoutParams.apply {
                        width = sizePx
                        height = sizePx
                    }
                    child.scaleType = ImageView.ScaleType.FIT_CENTER
                    child.requestLayout()
                    break
                }
            }
        }

        binding.bottomNav.setupWithNavController(navController)

        val sharedPreferences = getSharedPreferences("spotlight_prefs", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        val mode = if (isDarkMode)
            androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
        else
            androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                showDarkModeDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDarkModeDialog() {
        val sharedPreferences = getSharedPreferences("spotlight_prefs", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        val switch = com.google.android.material.materialswitch.MaterialSwitch(this).apply {
            isChecked = isDarkMode
            text = "Dark Mode"
            setPadding(48, 24, 48, 24)
        }

        com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
            .setTitle("Pengaturan")
            .setView(switch)
            .setPositiveButton("Tutup") { dialog, _ ->
                sharedPreferences.edit().putBoolean("dark_mode", switch.isChecked).apply()
                val mode = if (switch.isChecked)
                    androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
                else
                    androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
                androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(mode)
                dialog.dismiss()
            }
            .show()
    }
}