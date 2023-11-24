package com.example.foodorderingapp.ui.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.foodorderingapp.R
import com.example.foodorderingapp.databinding.ActivitySetLocationBinding

class SetLocationActivity : AppCompatActivity() {
    private val binding: ActivitySetLocationBinding by lazy {
        ActivitySetLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val countries = resources.getStringArray(R.array.country_names)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

        binding.listOfLocation.setOnItemClickListener { adapterView,
                                                        view, i, l ->
            binding.continueBtn.visibility = View.VISIBLE

        }

        binding.continueBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}