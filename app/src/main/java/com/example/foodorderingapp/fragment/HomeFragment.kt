package com.example.foodorderingapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapter.PopularAdapter
import com.example.foodorderingapp.databinding.FragmentHomeBinding
import com.example.foodorderingapp.model.Food

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.viewAllMenuBtn.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheet()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBannerView()
        displayMenuFoodItem()

    }

    private fun setBannerView(){
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3,ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun displayMenuFoodItem() {
        binding.recvFoodList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = PopularAdapter(getListFoods())
        binding.recvFoodList.adapter = adapter
    }

    private fun getListFoods(): List<Food> {
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


    companion object{

    }


}