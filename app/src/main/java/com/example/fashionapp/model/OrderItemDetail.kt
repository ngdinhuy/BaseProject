package com.example.fashionapp.model

import com.google.gson.annotations.SerializedName

class OrderItemDetail(
    @SerializedName("id") val _id: Int? = 0,
    val quantity: Int? = 0,
    val price: Double? = 0.0,
    val product: Product?
) {
}