package com.example.foodorderingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorderingapp.databinding.ActivityPayoutScreenBinding
import com.example.foodorderingapp.databinding.PayoutBottomSheetBinding
import com.example.foodorderingapp.ui.activity.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PayoutBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: PayoutBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = PayoutBottomSheetBinding.inflate(layoutInflater,container,false)
        binding.btnGoHome.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            dismiss()
        }
        return binding.root
    }

    companion object {
    }
}