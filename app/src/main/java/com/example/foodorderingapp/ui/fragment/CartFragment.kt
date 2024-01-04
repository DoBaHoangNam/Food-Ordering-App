package com.example.foodorderingapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.ui.activity.PayoutScreenActivity
import com.example.foodorderingapp.adapter.CartAdapter
import com.example.foodorderingapp.databinding.FragmentCartBinding
import com.example.foodorderingapp.model.OrderedFood
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var  databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var itemInCartList: ArrayList<OrderedFood> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveOrderedItem()

        binding.btnProceed.setOnClickListener {
            getOrderItemsDetail()

        }
        return binding.root
    }

    private fun getOrderItemsDetail() {

        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid?:""

        val orderIdRef: DatabaseReference = database.reference.child("user").child(userId).child("CartItems")

        val itemsOrderedList = mutableListOf<OrderedFood>()

        orderIdRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    val orderItems = foodSnapshot.getValue(OrderedFood::class.java)
                    orderItems?.let { itemsOrderedList.add(it) }
                }
                orderNow(itemsOrderedList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Order making failed. Please try again", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun orderNow(itemsOrderedList: MutableList<OrderedFood>) {
        if(isAdded && context!=null){
            val intent = Intent(requireContext(), PayoutScreenActivity::class.java)
            val bundle = Bundle()

            bundle.putSerializable("orderItemList", itemsOrderedList as ArrayList<OrderedFood>)
            intent.putExtras(bundle)
            startActivity(intent)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun retrieveOrderedItem() {

        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid?:""


        val foodReference: DatabaseReference = database.reference.child("user").child(userId).child("CartItems")


        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemInCartList.clear()

                for(foodSnapShot in snapshot.children){
                    val cartItem = foodSnapShot.getValue(OrderedFood::class.java)
                    cartItem?.let{
                        itemInCartList.add(it)
                    }


                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })

    }

    private fun setAdapter() {
        val adapter = CartAdapter(requireContext(),itemInCartList)
        binding.itemInCart.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.itemInCart.adapter = adapter

    }


    companion object {

    }
}