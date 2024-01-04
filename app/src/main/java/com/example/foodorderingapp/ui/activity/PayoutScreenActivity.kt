package com.example.foodorderingapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.foodorderingapp.PayoutBottomSheet
import com.example.foodorderingapp.adapter.CartAdapter
import com.example.foodorderingapp.databinding.ActivityPayoutScreenBinding
import com.example.foodorderingapp.model.OrderedFood
import com.example.foodorderingapp.ui.fragment.CartFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayoutScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityPayoutScreenBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalAmount: String
    private lateinit var orderItems: ArrayList<OrderedFood>
    private lateinit var userId: String
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayoutScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        setUserData()
        orderItems = ArrayList()
        totalAmount = ""

        val receivedIntent = intent
        if (receivedIntent != null) {
            val bundle: Bundle? = receivedIntent.extras
//            val orderItem: OrderedFood? = bundle?.getSerializable("OrderItem") as? OrderedFood
            val orderItemList: ArrayList<OrderedFood>? =
                bundle?.getSerializable("orderItemList") as? ArrayList<OrderedFood>
            if (orderItemList != null) {
                orderItems = orderItemList
            }
        }
        totalAmount = calculateTotalAmount().toString() + "đ"
        binding.tvTotal.text = totalAmount

        binding.btnPlaceOrder.setOnClickListener {
            val bottomSheetDialog = PayoutBottomSheet()
            bottomSheetDialog.show(supportFragmentManager, "Test")
        }

        binding.btnReturn.setOnClickListener {
            finish()
        }
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (index in 0 until orderItems.size) {
            val price = orderItems[index].foodPrice
            val quantity = orderItems[index].foodQuantity
            if (price != null && quantity != null) {
                val lastChar = price.last()
                val priceIntValue = if (lastChar == 'đ') {
                    price.dropLast(1).toInt()
                } else {
                    price.toInt()
                }
                totalAmount += priceIntValue * quantity.toInt()
            }
        }

        return totalAmount
    }


    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userRef = databaseReference.child("user").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java) ?: ""
                        val address = snapshot.child("address").getValue(String::class.java) ?: ""
                        val phone = snapshot.child("phone").getValue(String::class.java) ?: ""
                        binding.apply {
                            edtName.setText(name)
                            edtAddress.setText(address)
                            edtPhone.setText(phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }


    }
}


