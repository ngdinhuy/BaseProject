package com.example.fashionapp.ui.fashion.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.fashionapp.R
import com.example.fashionapp.TypeChat
import com.example.fashionapp.data.remote.response.ChatDetailResponse
import com.example.fashionapp.databinding.ItemMyChatMessageBinding
import com.example.fashionapp.databinding.ItemPartnerChatMessageBinding


class ChatAdapter(
    val context: Context,
    var listChatMessage: ArrayList<ChatDetailResponse>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == TypeChat.MY_CHAT){
            val databinding = ItemMyChatMessageBinding.inflate(LayoutInflater.from(context), parent, false)
            return MyChatViewHolder(databinding)
        } else if (viewType == TypeChat.PARTNER_CHAT) {
            val databinding = ItemPartnerChatMessageBinding.inflate(LayoutInflater.from(context), parent, false)
            return PartnerChatViewHolder(databinding)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_bottom, parent, false)
            return LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listChatMessage.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == TypeChat.MY_CHAT){
            (holder as? MyChatViewHolder)?.let {
                it.databinding.apply {
                    messageContent = listChatMessage[position].message
                    formatDate = listChatMessage[position].formatDate
                }
            }
        } else if (holder.itemViewType == TypeChat.PARTNER_CHAT) {
            (holder as? PartnerChatViewHolder)?.let {
                it.databinding.apply {
                    messageContent = listChatMessage[position].message
                    formatDate = listChatMessage[position].formatDate
                }
            }
        } else {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listChatMessage[position].id == 0){
            return -1
        } else if (listChatMessage[position].fromYou == true){
            TypeChat.MY_CHAT
        } else {
            TypeChat.PARTNER_CHAT
        }
    }

    inner class MyChatViewHolder(val databinding: ItemMyChatMessageBinding): RecyclerView.ViewHolder(databinding.root){

    }

    inner class PartnerChatViewHolder(
        val databinding: ItemPartnerChatMessageBinding
    ): RecyclerView.ViewHolder(databinding.root)

    private class LoadingViewHolder(itemView: View) : ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }
}