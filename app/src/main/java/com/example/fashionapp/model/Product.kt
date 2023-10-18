package com.example.fashionapp.model

import android.os.Parcelable
import com.example.fashionapp.utils.get2digit
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product(
    @SerializedName("id")
    val _id: Int?,
    val name: String?,
    val price: Double?,
    val description: String?,
    val discount: Int?,
    val image: List<String>?,
    val rate: Int?,
    val reviewNumber: String?,
    val quantity: Int? =  0,
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

    fun displayNewPrice(): Double{
        if (price != null){
            return if (discount != null){
                val oldPrice = price*(100-discount)/100
                oldPrice.get2digit()
            } else {
                price.toDouble()
            }
        }
        return 0.0
    }

    fun displayDiscount():String{
        return "-${discount}%"
    }

    fun displayQuantity(): String{
        return "Qty: $quantity"
    }
}