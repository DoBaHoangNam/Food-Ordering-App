package com.example.foodorderingapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapter.MenuAdapter
import com.example.foodorderingapp.databinding.FragmentSearchBinding
import com.example.foodorderingapp.model.Food

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private var originalMenuFood = getListFoods()
    private val filterMenuFood = mutableListOf<Food>()

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
        filterMenuFood.clear()
        filterMenuFood.addAll(originalMenuFood)
        adapter.notifyDataSetChanged()
    }

    private fun getListFoods(): MutableList<Food> {
        val list = mutableListOf<Food>()
        list.add(Food("Burger","$5", R.drawable.menu1 ))
        list.add(Food("Sandwich","$7", R.drawable.menu2 ))
        list.add(Food("Momo","$9", R.drawable.menu3 ))
        list.add(Food("Hamburger","$10", R.drawable.menu4 ))
        list.add(Food("Spaghetti","$11", R.drawable.menu5 ))
        list.add(Food("Fried Egg","$13", R.drawable.menu6 ))
        list.add(Food("Salat","$15", R.drawable.menu7 ))
        return list
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

        originalMenuFood.forEach { food ->
            if (food.foodName.contains(query, ignoreCase = true)) {
                filterMenuFood.add(food)
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