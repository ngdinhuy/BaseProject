package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionapp.Define
import com.example.fashionapp.data.remote.response.CartResponse
import com.example.fashionapp.databinding.ItemRvCartBinding
import com.example.fashionapp.model.CartModel

class CartAdapter(
    val listCart: List<CartResponse>,
    val context: Context,
    val showListPopupWindow: (cartModel: CartResponse, viewAnchor: View) -> Unit,
) : RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    var clickEvent:ClickEvent? = null

    fun passClickEvent(clickEvent: ClickEvent){
        this.clickEvent = clickEvent
    }

    inner class ViewHolder(val binding: ItemRvCartBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(item: CartResponse){
            binding.apply {
                cartModel = item
                if (!item.product.image.isNullOrEmpty()){
                    Glide.with(context).load(item.product.image[0]).into(image)
                }
                tvName.text = item.product.name

                btnAdd.setOnClickListener{
                    clickEvent?.editQuantity(item._id,1)
                }

                btnSubtract.setOnClickListener{
                    clickEvent?.editQuantity(item._id, -1)
                }

                btnMenu.setOnClickListener {
                    showListPopupWindow(item, it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvCartBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listCart[position])
    }

    override fun getItemCount(): Int = listCart.size

    interface ClickEvent{
        fun editQuantity(idProduct:Int, quantityChange:Int)
    }
}