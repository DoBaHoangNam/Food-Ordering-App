package com.example.foodorderingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorderingapp.databinding.ActivitySignUpScreenBinding

class SignUpActivity: AppCompatActivity() {
    private val binding: ActivitySignUpScreenBinding by lazy{
        ActivitySignUpScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.alreadyHaveAccTv.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}