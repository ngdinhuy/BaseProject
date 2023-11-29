package com.example.fashionapp.ui.seller.list_order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.data.remote.response.OrderItemSellerResponse
import com.example.fashionapp.databinding.ItemListOrderSellerBinding

class OrderItemSellerAdapter(
    val list: List<OrderItemSellerResponse>,
    val context: Context
) : RecyclerView.Adapter<OrderItemSellerAdapter.ViewHolder>() {
   class ViewHolder(val binding: ItemListOrderSellerBinding) : RecyclerView.ViewHolder(binding.root){

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListOrderSellerBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            orderItem = list[position]
        }
    }
}