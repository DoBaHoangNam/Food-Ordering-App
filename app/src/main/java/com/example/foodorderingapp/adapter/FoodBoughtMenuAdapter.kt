package com.example.foodorderingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.BoughtFood
import com.example.foodorderingapp.model.Food

class FoodBoughtMenuAdapter (private val items:MutableList<BoughtFood>):
    RecyclerView.Adapter<FoodBoughtMenuAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.foodBoughtImg)
        val price: TextView = itemView.findViewById(R.id.foodPriceTv)
        val restaurantName: TextView = itemView.findViewById(R.id.restaurantNameTv)
        val foodName: TextView = itemView.findViewById(R.id.FoodNameTv)
    }
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.previously_buy_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.foodName.text  = currentItem.foodName
        holder.price.text = currentItem.price
        holder.restaurantName.text  = currentItem.restaurantName
        holder.image.setImageResource(currentItem.image)

    }





}