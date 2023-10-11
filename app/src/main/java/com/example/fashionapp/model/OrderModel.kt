package com.example.fashionapp.model

import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("id") val  _id: Int? = 0,
    val date : String? = "",
    val total: Double? = 0.0,

)
