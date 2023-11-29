package com.example.fashionapp.data.remote.response

import com.example.fashionapp.Role
import com.google.gson.annotations.SerializedName

class UserInfoResponse(
    @SerializedName("id") val id_: Int? = 0,
    val name: String? = "",
    val email: String? = "",
    val phoneNumber: String? = "",
    val username: String? = "",
    val password: String? = "",
    val role: Int? = 0,
    val avatar: String? = "",
    val totalOrder: Int = 0,
    val totalOrderSeller: Int? = 0,
    val dob: String? = "",
    val mailPaypal: String? = "",
    val property: String? = "",
) {
    fun displayNumberOrder(): String{
        return "Already have ${if (role == Role.CUSTOMER) totalOrder else totalOrderSeller} orders"
    }

    fun displayMoney(): String{
        return "$property$"
    }
}