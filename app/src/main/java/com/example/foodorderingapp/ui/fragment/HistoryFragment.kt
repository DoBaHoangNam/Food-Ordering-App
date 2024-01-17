package com.example.foodorderingapp.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodorderingapp.adapter.FoodBoughtMenuAdapter
import com.example.foodorderingapp.databinding.FragmentHistoryBinding
import com.example.foodorderingapp.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: FoodBoughtMenuAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        retrieveBoughtHistory()




        return binding.root
    }

    private fun updateOrderStatus() {
        val itemPushKey = listOfOrderItem[listOfOrderItem.size - 1].itemPushKey
        val completeOrderReference = database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentRecieved").setValue(true)
        completeOrderReference.child("orderAccepted").setValue(true)
        completeOrderReference.child(itemPushKey).child("orderAccepted").setValue(true)
        Toast.makeText(
            requireContext(),
            "Thanks for your order. Have a good meal",
            Toast.LENGTH_SHORT
        ).show()
        binding.btnRecieved.visibility = View.INVISIBLE

    }

    private fun retrieveBoughtHistory() {
        binding.LNInforRecent.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""
        val buyItemRef: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")
        val sortQuery = buyItemRef.orderByChild("currentTime")

        sortQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                    listOfOrderItem.reverse()
                    if (listOfOrderItem.isNotEmpty()) {
                        setDataInRecentBuyItem()
                    }
                    val isOrderIsAccepted = listOfOrderItem[listOfOrderItem.size - 1].orderAccepted
                    val itemPushKey = listOfOrderItem[listOfOrderItem.size - 1].itemPushKey
                    Log.d("yyyyyyyyy", "$isOrderIsAccepted")

                    if (isOrderIsAccepted) {
                        binding.btnRecieved.visibility = View.VISIBLE
                        binding.btnRecieved.setOnClickListener {
                            if (itemPushKey != null) {
                                buyItemRef.child(itemPushKey).child("orderAccepted").setValue(false)
                            }
                            updateOrderStatus()
                        }

                    } else {
                        binding.btnRecieved.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun setDataInRecentBuyItem() {
        binding.LNInforRecent.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                foodNameTv.text = it.orderItems?.get(0)?.foodName.toString()
                foodPriceTv.text = it.orderItems?.get(0)?.foodPrice.toString()
                val image = it.orderItems?.get(0)?.foodImage.toString()
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(foodOrdered)

                listOfOrderItem.reverse()

//                val isOrderIsAccepted = listOfOrderItem[listOfOrderItem.size-1].orderAccepted
//                if(isOrderIsAccepted){
//                    btnRecieved.visibility = View.VISIBLE
//                }else{
//                    btnRecieved.visibility = View.INVISIBLE
//                }
                displayMenuFoodBoughtItem()


            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun displayMenuFoodBoughtItem() {
        binding.recvFoodBoughtList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        if (listOfOrderItem.isNotEmpty()) {
            // Get the orderItems from the most recent OrderDetails
            val recentOrderItems = listOfOrderItem[listOfOrderItem.size - 1].orderItems

            // Check if orderItems is not null before creating the adapter
            if (recentOrderItems != null) {
                val adapter = FoodBoughtMenuAdapter(requireContext(), recentOrderItems)
                binding.recvFoodBoughtList.adapter = adapter
                Log.d("bbbbbbbbbbbbb", "bbbbbbbbbbbb")
            } else {
                Log.d("bbbbbbbbbbbbb", "OrderItems in the most recent OrderDetails is null")
            }
        } else {
            Log.d("bbbbbbbbbbbbb", "List of order items is empty")
        }
    }


    companion object {

    }
}