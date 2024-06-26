package com.example.fashionapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class UserModel(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("phoneNumber") val phoneNumber: String? = "",
    @SerializedName("username") val username: String? = "",
    @SerializedName("password") val password: String? = "",
    @SerializedName("avatar") val avatar: String? = "",
    @SerializedName("dob") val dob: String? = "",
    @SerializedName("role") val role: Int? = -1,
) : Parcelable{
}