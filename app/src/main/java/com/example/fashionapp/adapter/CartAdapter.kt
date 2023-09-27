package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionapp.Define
import com.example.fashionapp.databinding.ItemRvCartBinding
import com.example.fashionapp.model.CartModel

class CartAdapter(
    val listCart: List<CartModel>,
    val context: Context,
    val showListPopupWindow: (cartModel: CartModel, viewAnchor: View) -> Unit,
) : RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    var clickEvent:ClickEvent? = null

    fun passClickEvent(clickEvent: ClickEvent){
        this.clickEvent = clickEvent
    }

    inner class ViewHolder(val binding: ItemRvCartBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(item: CartModel){
            binding.apply {
                cartModel = item
                Glide.with(context).load(Define.listProduct[item.idProduct!!-1].image).into(image)
                tvName.text = Define.listProduct[item.idProduct!!-1].displayName()

                btnAdd.setOnClickListener{
                    clickEvent?.addQuantity(item.idProduct!!)
                    tvQuantity.text = (tvQuantity.text.toString().toInt()+1).toString()
                }

                btnSubtract.setOnClickListener{
                    clickEvent?.subtractQuantity(item.idProduct!!,tvQuantity.text.toString().toInt())
                    if (tvQuantity.text.toString().toInt() == 1){
                        (listCart as ArrayList).removeAt(position)
                        notifyItemRemoved(position)
                    }else{
                        tvQuantity.text = (tvQuantity.text.toString().toInt()-1).toString()
                    }
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
        fun addQuantity(idProduct:Int)

        fun subtractQuantity(idProduct: Int,currentQuantity: Int)
    }
}