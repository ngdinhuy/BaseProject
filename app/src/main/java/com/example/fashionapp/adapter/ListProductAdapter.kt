package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.databinding.ItemListProductBinding
import com.example.fashionapp.model.Product

class ListProductAdapter(val context: Context,
    val list: List<Product>) : RecyclerView.Adapter<ListProductAdapter.ViewHolder>(){
    var itemClickEvent:ItemClickEvent? = null
    fun passValueItemClickEvent(itemClickEvent: ItemClickEvent){
        this.itemClickEvent = itemClickEvent
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListProductBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databinding.apply {
            product = list[position]
            clItem.setOnClickListener {
                itemClickEvent?.goToProductDetail(list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(val databinding: ItemListProductBinding):RecyclerView.ViewHolder(databinding.root){

    }

    interface ItemClickEvent{
        fun goToProductDetail(product: Product)
    }
}