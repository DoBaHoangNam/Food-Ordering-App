package com.example.foodorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.adapter.FoodBoughtMenuAdapter
import com.example.foodorderingapp.adapter.NotificationAdapter
import com.example.foodorderingapp.databinding.NotificationBottomSheetBinding
import com.example.foodorderingapp.model.BoughtFood
import com.example.foodorderingapp.model.Notifications
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: NotificationBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NotificationBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayNotificationItem()

    }

    private fun displayNotificationItem() {
        binding.recvNotificationList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = NotificationAdapter(getListNotifications())
        binding.recvNotificationList.adapter = adapter
        binding.btnReturn.setOnClickListener {
            dismiss()
        }
    }

    private fun getListNotifications(): MutableList<Notifications> {
        val list = mutableListOf<Notifications>()
        list.add(Notifications("Your order has been Canceled Successfully", R.drawable.icon_sad ))
        list.add(Notifications("Order has been taken by the driver", R.drawable.icon_delivery ))
        list.add(Notifications("Congrats Your Order Placed", R.drawable.icon_success ))

        return list
    }

    companion object {

    }
}