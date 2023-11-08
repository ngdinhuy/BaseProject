package com.example.fashionapp.data.remote.response

data class ChatDetailResponse(
    val id:Int? = 0,
    val message: String? = "",
    val fromYou: Boolean? = false,
    val formatDate: String? = ""
) {
}