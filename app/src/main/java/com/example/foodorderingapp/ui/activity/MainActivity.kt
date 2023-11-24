package com.example.foodorderingapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodorderingapp.NotificationBottomSheet
import com.example.foodorderingapp.R
import com.example.foodorderingapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var navController = findNavController(R.id.fragmentContainerView2)
        var bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomnav.setupWithNavController(navController)
        binding.icNotify.setOnClickListener {
            val bottomSheetDialog = NotificationBottomSheet()
            bottomSheetDialog.show(supportFragmentManager, "Test")
        }
    }
}