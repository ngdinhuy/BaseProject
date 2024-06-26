package com.example.fashionapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status") var status: Int,
    @SerializedName("errors") var errors: List<String>,
    @SerializedName("data") var dataResponse: T,
)