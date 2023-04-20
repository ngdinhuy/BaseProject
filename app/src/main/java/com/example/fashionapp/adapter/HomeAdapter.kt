package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.Define
import com.example.fashionapp.databinding.ItemRvHomeBinding
import com.example.fashionapp.model.Product

class HomeAdapter(
    val context: Context,
    val map: Map<String, Any?>): RecyclerView.Adapter<HomeAdapter.HomeItemViewHolder>() {

    class HomeItemViewHolder(val binding: ItemRvHomeBinding)
        :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val binding = ItemRvHomeBinding.inflate(LayoutInflater.from(context), parent, false)
        return HomeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.binding.apply {
            if (position == 0){
                title = "MEN"
                quotes = Define.MENS_QUOTES
                rvItem.apply {
                    adapter = HomeItemListAdapter(context, map[Define.MEN_CLOTHES] as ArrayList<Product>)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }
            }else{
                title = "WOMEN"
                quotes = Define.WOMEN_QUOTES
                rvItem.apply {
                    adapter = HomeItemListAdapter(context, map[Define.WOMEN_CLOTHES] as ArrayList<Product>)
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }
            }

        }
    }

    override fun getItemCount(): Int = map.size
}