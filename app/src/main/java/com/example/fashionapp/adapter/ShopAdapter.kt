package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionapp.Define
import com.example.fashionapp.R
import com.example.fashionapp.databinding.ItemRvClothingCategoryBinding
import com.example.fashionapp.databinding.ItemShopFragmentBinding
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.model.Product
import java.util.zip.Inflater

class ShopAdapter(
    val context: Context,
    var list: List<Product>
) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    var goToDetailEvent : GoToDetailEvent? = null

    class ViewHolder(val binding: ItemRvClothingCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRvClothingCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.product = list[position]
        if (list[position].image != null) {
            Glide.with(context).load(list[position].image).into(holder.binding.ivItem)
        }
        holder.binding.clItem.setOnClickListener{
            goToDetailEvent?.goToDetail(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    interface GoToDetailEvent{
        fun goToDetail(product: Product)
    }
}