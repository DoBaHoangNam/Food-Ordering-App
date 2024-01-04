package com.example.foodorderingapp.model

import java.io.Serializable


data class Item(
    val foodName: String? = null,
    val price: String? = null,
    val description: String? = null,
    val image: String? = null,
    val ingredient: String? = null
): Serializable