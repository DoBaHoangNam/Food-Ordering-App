package com.example.foodorderingapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapter.MenuAdapter
import com.example.foodorderingapp.databinding.FragmentSearchBinding
import com.example.foodorderingapp.model.Food
import com.example.foodorderingapp.model.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var originalMenuFood: MutableList<Item>
    private val filterMenuFood = mutableListOf<Item>()
    private lateinit var  databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        binding.recvFoodList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = MenuAdapter(filterMenuFood, requireContext())
        binding.recvFoodList.adapter = adapter
        setupSearchView()
        showAllMenu()
        return binding.root
    }

    private fun showAllMenu() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        originalMenuFood = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    val menuItem  = foodSnapshot.getValue(Item::class.java)
                    menuItem?.let { originalMenuFood.add(it) }
                }
                filterMenuFood.clear()
                filterMenuFood.addAll(originalMenuFood)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })


    }


    private fun setupSearchView() {
        binding.searchEdt.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
                filterMenuItems(query)
                return true
            }
        })
    }


    private fun filterMenuItems(query: String){
        filterMenuFood.clear()

        originalMenuFood.forEach { item ->
            if (item.foodName?.contains(query, ignoreCase = true) == true) {
                filterMenuFood.add(item)
            }
        }
//        originalMenuFood.forEachIndexed { index, food ->
//            if(food.foodName.contains(query,ignoreCase = true)){
//                filterMenuFood.add(food)
//            }
//        }
        adapter.notifyDataSetChanged()
    }


    companion object {

    }
}