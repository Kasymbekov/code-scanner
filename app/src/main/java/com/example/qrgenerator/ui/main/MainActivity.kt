package com.example.qrgenerator.ui.main

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.qrgenerator.R
import com.example.qrgenerator.databinding.ActivityMainBinding
import com.example.qrgenerator.ui.generator.GeneratorFragment
import com.example.qrgenerator.ui.history.HistoryFragment
import com.example.qrgenerator.ui.scanner.ScannerFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host_main) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // init splashscreen before init UI
        installSplashScreen().setOnExitAnimationListener { splashScreenViewProvider ->
            val view = splashScreenViewProvider.iconView
            val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.2f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.2f, 1f)
            ).apply {
                duration = 600
                interpolator = AccelerateDecelerateInterpolator()
                doOnEnd {
                    splashScreenViewProvider.remove()
                }
            }
            scaleUp.start()
        }

        // transparent system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavView.setupWithNavController(navController) // to sync with bottom nav

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
//                R.id.scannerFragment -> startActivity(Intent(this, ScannerActivity::class.java))
                R.id.scannerFragment -> navController.navigate(R.id.scannerFragment)
                R.id.generatorFragment -> navController.navigate(R.id.generatorFragment)
                R.id.historyFragment -> navController.navigate(R.id.historyFragment)
            }
            true
        }

        // request permission for camera
        val requestPermissionLauncher =
            this.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_SHORT).show()
                }
            }
        requestPermissionLauncher.launch(
            Manifest.permission.CAMERA
        )
    }
}