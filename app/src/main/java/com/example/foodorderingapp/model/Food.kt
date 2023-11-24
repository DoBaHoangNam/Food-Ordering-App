package com.example.foodorderingapp.model

import java.io.Serializable


data class Food(
    val foodName: String,
    val price: String,
    val image: Int
): Serializable