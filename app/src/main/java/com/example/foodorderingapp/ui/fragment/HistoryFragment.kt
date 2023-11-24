package com.example.foodorderingapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapter.FoodBoughtMenuAdapter
import com.example.foodorderingapp.adapter.PopularAdapter
import com.example.foodorderingapp.databinding.FragmentHistoryBinding
import com.example.foodorderingapp.model.BoughtFood
import com.example.foodorderingapp.model.Food

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayMenuFoodBoughtItem()

    }

    private fun displayMenuFoodBoughtItem() {
        binding.recvFoodBoughtList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = FoodBoughtMenuAdapter(getListFoods())
        binding.recvFoodBoughtList.adapter = adapter
    }

    private fun getListFoods(): MutableList<BoughtFood> {
        val list = mutableListOf<BoughtFood>()
        list.add(BoughtFood("Burger","$5", "A restaurant", R.drawable.menu1 ))
        list.add(BoughtFood("Burger","$7", "B restaurant", R.drawable.menu2 ))
        list.add(BoughtFood("Burger","$9", "C restaurant", R.drawable.menu3 ))
        list.add(BoughtFood("Burger","$3", "D restaurant", R.drawable.menu4 ))
        list.add(BoughtFood("Burger","$4", "E restaurant", R.drawable.menu5 ))
        list.add(BoughtFood("Burger","$6", "F restaurant", R.drawable.menu6 ))
        list.add(BoughtFood("Burger","$1", "G restaurant", R.drawable.menu7 ))

        return list
    }

    companion object {

    }
}