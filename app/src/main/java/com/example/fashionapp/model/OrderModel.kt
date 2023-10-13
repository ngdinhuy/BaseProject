package com.example.fashionapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderModel(
    @SerializedName("id") val  _id: Int? = 0,
    val date : String? = "",
    val total: Double? = 0.0,
    val discount: Double? = 0.0,
    val quantity: Int? = 0
) : Parcelable{
    fun displayOrderNumber() = "Order №+ $_id"

    fun displayQuantity() = "$quantity items"
}
