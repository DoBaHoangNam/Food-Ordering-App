package com.example.foodorderingapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingapp.R
import com.example.foodorderingapp.databinding.ActivityFoodDetailsBinding
import com.example.foodorderingapp.model.Food

class ActivityFoodDetails : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedIntent = intent
        if (receivedIntent != null) {
            val bundle: Bundle? = receivedIntent.extras
            val foodItem: Food? = bundle?.getSerializable("MenuItem") as? Food

            if (foodItem != null) {
                val foodName = foodItem.foodName
                val foodImage = foodItem.image
                binding.tvDetail.text = foodName
                binding.imgFood.setImageResource(foodImage)
            }
            binding.btnBack.setOnClickListener {
                finish()
            }
        }
    }
}