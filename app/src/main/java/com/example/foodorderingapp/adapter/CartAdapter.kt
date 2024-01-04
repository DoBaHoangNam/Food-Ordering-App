package com.example.foodorderingapp.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.R
import com.example.foodorderingapp.model.OrderedFood
import com.example.foodorderingapp.ui.fragment.CartFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(private val context: Context,
                  private var items: MutableList<OrderedFood>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val userId = auth.currentUser?.uid ?: ""
    private val cartItemsReference: DatabaseReference =
        database.reference.child("user").child(userId).child("CartItems")

    var itemQuantities = IntArray(items.size){1}
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
        val uriString = currentItem.foodImage
        val uri = Uri.parse(uriString)
        Log.d("image", "food url: $uriString")


        holder.foodName.text = currentItem.foodName
        holder.price.text = currentItem.foodPrice
        Glide.with(context).load(uri).into(holder.image)
        holder.quantities.text = currentItem.foodQuantity



        fun getUniqueKeyAtPosition(position: Int, onComplete:(String?) -> Unit) {

            cartItemsReference.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey: String?=null

                    snapshot.children.forEachIndexed{ index, dataSnapshot ->
                        uniqueKey = dataSnapshot.key
                        return@forEachIndexed
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        fun updateQuantityInDatabase(position: Int, newQuantity: Int) {
            getUniqueKeyAtPosition(position) { uniqueKey ->
                if (uniqueKey != null) {
                    val quantityUpdateMap = HashMap<String, Any>()
                    quantityUpdateMap["foodQuantity"] = newQuantity.toString()

                    cartItemsReference.child(uniqueKey).updateChildren(quantityUpdateMap)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Quantity updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to update quantity", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        fun removeItem(position: Int, uniqueKey: String) {
            if (uniqueKey != null){
                cartItemsReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    items.removeAt(position)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                    itemQuantities = itemQuantities.filterIndexed { index, i -> index!=position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,items.size)
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun deleteItem(position: Int){
            getUniqueKeyAtPosition(position){uniqueKey ->
                if (uniqueKey != null){
                    removeItem(position, uniqueKey)
                }
            }

        }

        fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                holder.quantities.text = itemQuantities[position].toString()
                updateQuantityInDatabase(position, itemQuantities[position])
            } else {
                deleteItem(position)
            }
        }

        fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                holder.quantities.text = itemQuantities[position].toString()
                updateQuantityInDatabase(position, itemQuantities[position])
            }
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

