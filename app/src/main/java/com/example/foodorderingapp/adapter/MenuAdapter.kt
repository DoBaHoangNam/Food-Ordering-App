package com.example.foodorderingapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.Food
import com.example.foodorderingapp.model.Item
import com.example.foodorderingapp.ui.activity.ActivityFoodDetails

class MenuAdapter(private val items: MutableList<Item>, private val requireContext: Context):
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private val itemClickListener: OnClickListener ?= null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        init {
//
//        }
        val image: ImageView = itemView.findViewById(R.id.imgFood)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
        val foodName: TextView = itemView.findViewById(R.id.tvNameOfFood)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_food_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = items[position]
        val uriString = currentItem.image
        val uri = Uri.parse(uriString)
        holder.foodName.text = currentItem.foodName
        holder.price.text = currentItem.price
        Glide.with(requireContext).load(uri).into(holder.image)

        holder.itemView.setOnClickListener {
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                itemClickListener?.onItemClick(position)
//            }
            val intent = Intent(requireContext, ActivityFoodDetails::class.java)
            val bundle = Bundle()
            bundle.putSerializable("MenuItem", items[position])
            intent.putExtras(bundle)
            requireContext.startActivity(intent)

        }
    }
    interface OnClickListener{
        fun onItemClick(position: Int)

    }
}


