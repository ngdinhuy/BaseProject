package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.Define
import com.example.fashionapp.R
import com.example.fashionapp.databinding.ItemShopFragmentBinding
import java.util.zip.Inflater

class ShopAdapter(
    val context: Context,
    val list: List<String>
) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    var itemClickEvent: ItemClickEvent? = null

    fun passDataItemClickEvent(itemClickEvent: ItemClickEvent){
        this.itemClickEvent = itemClickEvent
    }
    class ViewHolder(val itemShop: ItemShopFragmentBinding) :
        RecyclerView.ViewHolder(itemShop.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val databinding =
            ItemShopFragmentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(databinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemShop.apply {
            cate = list[position]
            imgCategory.setImageResource(
                when (cate) {
                    Define.MEN_CLOTHES -> R.drawable.ic_men_clothing
                    Define.WOMEN_CLOTHES -> R.drawable.ic_women_clothing
                    Define.ELECTRONICS -> R.drawable.ic_electronics
                    else -> R.drawable.ic_jewelry
                }
            )
        }
        holder.itemShop.cardView.setOnClickListener{
            itemClickEvent?.itemClick(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    interface ItemClickEvent{
        fun itemClick(cate:String)
    }
}