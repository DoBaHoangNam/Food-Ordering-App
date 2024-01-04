package com.example.foodorderingapp.model

import java.io.Serializable

data class OrderedFood (
    var foodName: String ?=null,
    var foodPrice: String ?=null,
    var foodDescription: String ?=null,
    var foodQuantity: String ?=null,
    var foodImage: String ?=null
): Serializable