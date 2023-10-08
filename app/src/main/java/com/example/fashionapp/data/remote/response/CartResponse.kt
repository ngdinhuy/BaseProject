package com.example.fashionapp.data.remote.response

import com.example.fashionapp.model.Product
import com.google.gson.annotations.SerializedName

class CartResponse(
    @SerializedName("id") val  _id : Int,
    val quantity: Int,
    val totalPrice: Double,
    val product: Product
) {
    fun displayQuantity():String = quantity.toString()
}