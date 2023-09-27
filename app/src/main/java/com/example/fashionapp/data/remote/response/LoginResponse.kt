package com.example.fashionapp.data.remote.response

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("username") val username: String
) {
}