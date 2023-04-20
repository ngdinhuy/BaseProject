package com.example.fashionapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product(
    @SerializedName("id")
    val _id: Int?,
    val title: String?,
    val price: Double?,
    val description: String?,
    val image: String?,
    val rating: Rating?
): Parcelable{
}
@Parcelize
class Rating(
    val rate: Double,
    val count: Int
): Parcelable