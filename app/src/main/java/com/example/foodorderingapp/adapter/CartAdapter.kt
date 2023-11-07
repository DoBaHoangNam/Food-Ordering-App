package com.example.foodorderingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.OrderedFood

class CartAdapter(private var items: MutableList<OrderedFood>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
  
    val itemQuantities = IntArray(items.size){1}
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgFood)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
        val foodName: TextView = itemView.findViewById(R.id.tvNameOfFood)
        val quantities: TextView = itemView.findViewById(R.id.quantitiesTv)
        val minusBtn: ImageView =  itemView.findViewById(R.id.minusBtn)
        val plusBtn: ImageView =  itemView.findViewById(R.id.plusBtn)
        val deleteBtn: ImageView =  itemView.findViewById(R.id.trashBtn)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_in_cart_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.foodName.text = currentItem.foodName
        holder.price.text = currentItem.price
        holder.image.setImageResource(currentItem.image)
        holder.quantities.text = currentItem.quantity


        fun decreaseQuantity(position: Int){
            if(itemQuantities[position]>1){
                itemQuantities[position]--
                holder.quantities.text = itemQuantities[position].toString()
            }
        }

        fun increaseQuantity(position: Int){
            if(itemQuantities[position]<10){
                itemQuantities[position]++
                holder.quantities.text = itemQuantities[position].toString()
            }
        }

        fun deleteItem(position: Int){
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,items.size)

        }

        holder.minusBtn.setOnClickListener{
            decreaseQuantity(position)
        }
        holder.plusBtn.setOnClickListener {
            increaseQuantity(position)
        }
        holder.deleteBtn.setOnClickListener {
            deleteItem(position)
        }
    }


}

