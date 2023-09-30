package com.example.fashionapp.model

import com.google.gson.annotations.SerializedName

class CategoryModel(
    @SerializedName("id")val id_:Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String
) {
}