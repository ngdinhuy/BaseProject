package com.example.fashionapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class PagingBaseResponse<T>(
    @SerializedName("status") var status: Int,
    @SerializedName("errors") var errors: List<String>,
    @SerializedName("data") var dataResponse: T,
    @SerializedName("offset") var offset: Int
)