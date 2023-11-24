package com.example.foodorderingapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorderingapp.databinding.ActivityLoginScreenBinding

class LoginActivity:AppCompatActivity() {
    private val binding: ActivityLoginScreenBinding by lazy{
        ActivityLoginScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.dontHaveAccTv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginBtn.setOnClickListener{
            val intent = Intent(this, SetLocationActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}