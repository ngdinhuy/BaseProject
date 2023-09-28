package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionapp.databinding.ItemRvClothingBinding
import com.example.fashionapp.model.Product

class HomeItemListAdapter(val context: Context, val list: ArrayList<Product>) :
    RecyclerView.Adapter<HomeItemListAdapter.HomeItemViewHolder>() {
    var goToDetailEvent : GoToDetailEvent? = null

    class HomeItemViewHolder(val binding: ItemRvClothingBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val binding = ItemRvClothingBinding.inflate(LayoutInflater.from(context), parent, false)
        return HomeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.binding.product = list[position]
        Glide.with(context).load(list[position].image).into(holder.binding.ivItem)
        holder.binding.clItem.setOnClickListener{
            goToDetailEvent?.goToDetail(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    interface GoToDetailEvent{
        fun goToDetail(product: Product)
    }
}