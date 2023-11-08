package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.data.remote.response.ChatListResponse
import com.example.fashionapp.databinding.ItemFavoritesBinding
import com.example.fashionapp.model.Product

class ChatListApdater(val context: Context,
                      val list: List<ChatListResponse>) : RecyclerView.Adapter<ChatListApdater.ViewHolder>(){
    var itemClickEvent:ItemClickEvent? = null
    fun passValueItemClickEvent(itemClickEvent: ItemClickEvent){
        this.itemClickEvent = itemClickEvent
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFavoritesBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databinding.apply {
            chatListResponse = list[position]
            root.setOnClickListener {
                itemClickEvent?.goToProductDetail(list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val databinding: ItemFavoritesBinding):RecyclerView.ViewHolder(databinding.root){

    }

    interface ItemClickEvent{
        fun goToProductDetail(chatMessageResponse: ChatListResponse)
    }
}