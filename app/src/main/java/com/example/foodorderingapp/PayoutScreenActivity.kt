package com.example.foodorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingapp.databinding.ActivityPayoutScreenBinding

class PayoutScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityPayoutScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayoutScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnPlaceOrder.setOnClickListener {
            val bottomSheetDialog = PayoutBottomSheet()
            bottomSheetDialog.show(supportFragmentManager, "Test")
        }
    }
}