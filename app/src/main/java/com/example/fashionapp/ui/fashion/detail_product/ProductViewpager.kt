package com.example.fashionapp.ui.fashion.detail_product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.fashionapp.databinding.ItemViewPagerProductBinding

class ProductViewpage(
    val context: Context,
    val urls: List<String>,
): RecyclerView.Adapter<ProductViewpage.ViewHolder>() {

    inner class ViewHolder(val databinding: ItemViewPagerProductBinding): RecyclerView.ViewHolder(databinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewPagerProductBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databinding.url = urls[position]
    }


}