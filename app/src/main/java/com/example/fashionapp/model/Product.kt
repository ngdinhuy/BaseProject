package com.example.fashionapp.model

import android.os.Parcelable
import com.example.fashionapp.utils.get2digit
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product(
    @SerializedName("id")
    val _id: Int? = 0,
    val name: String? = "",
    val price: Double? = 0.0,
    val description: String? = "",
    val discount: Int? = 0,
    val image: List<String>? = listOf(),
    val rate: Int? = 0,
    val reviewNumber: String? = "",
    val quantity: Int? =  0,
    val categoryName: String? = "",
    val idCategory: Int?=  0,
    @SerializedName("sellerid") val user:UserModel? = UserModel()
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

    fun displayNameSeller(): String{
        return user?.name ?: ""
    }
}