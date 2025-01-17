package com.example.qrgenerator

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.qrgenerator.databinding.ActivityMainBinding
import com.example.qrgenerator.ui.activities.ScannerActivity
import com.example.qrgenerator.ui.fragments.GenerateFragment
import com.example.qrgenerator.ui.fragments.ProfileFragment
import com.example.qrgenerator.ui.fragments.ScanFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val generateFragment = GenerateFragment()
        val scanFragment = ScanFragment()
        val profileFragment = ProfileFragment()
        setCurrentFragment(generateFragment)

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.generate -> setCurrentFragment(generateFragment)
                R.id.scan -> startActivity(Intent(this, ScannerActivity::class.java))
                R.id.profile -> setCurrentFragment(profileFragment)
            }
            true
        }

        Toast.makeText(this, this.javaClass.name, Toast.LENGTH_LONG).show()

        // request permission for camera
        val requestPermissionLauncher =
            this.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
                }
            }
        requestPermissionLauncher.launch(
            Manifest.permission.CAMERA
        )
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}