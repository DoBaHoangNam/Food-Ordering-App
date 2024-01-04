package com.example.foodorderingapp.ui.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.foodorderingapp.R
import com.example.foodorderingapp.databinding.ActivityFoodDetailsBinding
import com.example.foodorderingapp.model.Food
import com.example.foodorderingapp.model.Item
import com.example.foodorderingapp.model.OrderedFood
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityFoodDetails : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailsBinding
    private var foodName: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredients: String? = null
    private var foodPrice: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val receivedIntent = intent
        if (receivedIntent != null) {
            val bundle: Bundle? = receivedIntent.extras
            val foodItem: Item? = bundle?.getSerializable("MenuItem") as? Item

            if (foodItem != null) {
                foodName = foodItem.foodName
                foodImage = foodItem.image
                foodDescription = foodItem.description
                foodPrice =foodItem.price
                foodIngredients = foodItem.ingredient

                binding.tvFoodName.text = foodName
                binding.tvDetail.text = foodDescription
                binding.tvDetailIngredient.text = foodItem.ingredient
                Glide.with(this@ActivityFoodDetails).load(Uri.parse(foodImage)).into(binding.imgFood)

            }

        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnAddToCart.setOnClickListener {
            addItemToCart()
            finish()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""

        // Create a cartItems object
        val cartItem = OrderedFood(
            foodName.toString(),
            foodPrice.toString(),
            foodDescription.toString(),
            "1",
            foodImage.toString()
        )

        // Check if the item already exists in the user's cart
        val cartItemsQuery = database.child("user").child(userId).child("CartItems")
            .orderByChild("foodName").equalTo(foodName)

        cartItemsQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Item already exists in the cart
                    Toast.makeText(this@ActivityFoodDetails, "Item already exists in your cart", Toast.LENGTH_SHORT).show()
                } else {
                    // Item does not exist, add it to the cart
                    database.child("user").child(userId).child("CartItems").push().setValue(cartItem)
                        .addOnSuccessListener {
                            Toast.makeText(this@ActivityFoodDetails, "Item added to your cart successfully", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this@ActivityFoodDetails, "Failed to add item to cart", Toast.LENGTH_SHORT).show()
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                Toast.makeText(this@ActivityFoodDetails, "Database error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}