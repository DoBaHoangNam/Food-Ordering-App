package com.example.foodorderingapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapter.CartAdapter
import com.example.foodorderingapp.adapter.PopularAdapter
import com.example.foodorderingapp.databinding.FragmentCartBinding
import com.example.foodorderingapp.model.OrderedFood


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayFoodOrder()
    }

    private fun displayFoodOrder(){
        binding.itemInCart.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val adapter = CartAdapter(getListFoodOrdered())
        binding.itemInCart.adapter = adapter
    }

    private fun getListFoodOrdered(): MutableList<OrderedFood>{
        val list = mutableListOf<OrderedFood>()
        list.add(OrderedFood("Burger","$5","1",R.drawable.menu1))
        list.add(OrderedFood("Sandwich","$7","1",R.drawable.menu2))
        list.add(OrderedFood("Momo","$9","1",R.drawable.menu3))
        list.add(OrderedFood("Hamburger","$10","1",R.drawable.menu4))
        return list
    }

    companion object {

    }
}