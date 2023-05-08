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
    fun displayRatingFloat() : Float {
        rating?.let {
            return rating.rate.toFloat()
        }
        val x = 5
        return x.toFloat()
    }
    fun displayRatingCount(): Int? = rating?.count

    fun displayName():String?{
        title?.let {
            val listName = it.split(" ")
            if (listName.size > 1){
                return listName[0] + " " + listName[1]
            }
        }
        return null
    }
}
@Parcelize
class Rating(
    val rate: Double,
    val count: Int
): Parcelable