package com.example.fashionapp.data.remote.response

import android.os.Parcelable
import com.example.fashionapp.model.Product
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class CategoryAndProductResponse (
    @SerializedName("idCategory") val idCategory:Int?,
    val titleCategory: String?,
    val descriptionCategory: String?,
    val products: List<Product>
        ): Parcelable{
}