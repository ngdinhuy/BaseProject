package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.Define
import com.example.fashionapp.databinding.ItemFavoritesBinding
import com.example.fashionapp.databinding.ItemFavoritesBindingImpl
import com.example.fashionapp.databinding.ItemListProductBinding
import com.example.fashionapp.model.Product

class FavoritesAdapter(val context: Context,
                         val list: List<Int>) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>(){
    var itemClickEvent:ItemClickEvent? = null
    fun passValueItemClickEvent(itemClickEvent: ItemClickEvent){
        this.itemClickEvent = itemClickEvent
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFavoritesBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databinding.apply {
            product = Define.listProduct[list[position]-1]
            clItem.setOnClickListener {
//                itemClickEvent?.goToProductDetail(list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val databinding: ItemFavoritesBinding):RecyclerView.ViewHolder(databinding.root){

    }

    interface ItemClickEvent{
        fun goToProductDetail(product: Product)
    }
}