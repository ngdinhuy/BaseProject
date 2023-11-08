package com.example.fashionapp.data.remote.response

data class ChatListResponse (
    val idUser : Int? = 0,
    val name : String? = "",
    val avatar : String? = "",
    val lastMessage: String? = "",
    val formatDate: String? = "",
    val fromYou: Boolean? = true,
    val numberUnreadMessage : Int? = 0
        ){
    fun displayMessage(): String{
        return if (fromYou == false){
            "$lastMessage"
        } else {
            "You: $lastMessage"
        }
    }

    fun hasUnreadMessage(): Boolean{
        return numberUnreadMessage!! > 0
    }
}