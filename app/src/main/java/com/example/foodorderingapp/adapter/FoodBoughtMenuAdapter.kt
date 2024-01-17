package com.example.foodorderingapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.Item
import com.example.foodorderingapp.model.OrderedFood
import com.example.foodorderingapp.ui.activity.ActivityFoodDetails

class FoodBoughtMenuAdapter(
    private val context: Context,
    private val items: MutableList<OrderedFood>
) :
    RecyclerView.Adapter<FoodBoughtMenuAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.foodBoughtImg)
        val price: TextView = itemView.findViewById(R.id.foodPriceTv)
        val foodName: TextView = itemView.findViewById(R.id.foodNameTv)
        val buyAgain: Button = itemView.findViewById(R.id.btnBuyAgain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.previously_buy_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.foodName.text = currentItem.foodName
        holder.price.text = currentItem.foodPrice
        val uriString = currentItem.foodImage
        val uri = Uri.parse(uriString)
        Glide.with(context).load(uri).into(holder.image)

        val item = Item(
            currentItem.foodName,
            currentItem.foodPrice,
            currentItem.foodDescription,
            currentItem.foodImage,
            currentItem.foodDescription
        )
        holder.buyAgain.setOnClickListener {
            val intent = Intent(context, ActivityFoodDetails::class.java)
            val bundle = Bundle()
            bundle.putSerializable("MenuItem", item)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }


}