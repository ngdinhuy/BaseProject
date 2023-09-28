package com.example.fashionapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product(
    @SerializedName("id")
    val _id: Int?,
    val name: String?,
    val price: Double?,
    val description: String?,
    val image: String?,
    val rate: Int?,
    val reviewNumber: String?,
    @SerializedName("idSeller") val user:UserModel?
): Parcelable{
    fun displayRatingFloat() : Float {
        rate?.let {
            return it.toFloat()
        }
        val x = 5
        return x.toFloat()
    }
    fun displayRatingCount(): Int? = reviewNumber?.toInt()

    fun displayName():String?{
        return name
    }
}