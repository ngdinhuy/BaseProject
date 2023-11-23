package com.example.fashionapp.ui.fashion.profile.order_detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.databinding.ItemRvOrderDetailBinding
import com.example.fashionapp.model.OrderItemDetail
import com.example.fashionapp.model.Product

class OrderDetailAdapter(
    var listOrderDetail: List<OrderItemDetail>,
    val context: Context,
//    val onItemClick: ((idProduct: Product) -> Unit)?
): RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {

    var eventClick: EventClick? = null

    inner class ViewHolder(val binding:ItemRvOrderDetailBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRvOrderDetailBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return listOrderDetail.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            orderItem = listOrderDetail[position]
            tvReview.setOnClickListener {
                eventClick?.clickOpenReview(listOrderDetail[position]._id ?: 0)
            }
            image.setOnClickListener {
                eventClick?.clickOpenDetailProduct(listOrderDetail[position].product?._id ?: 0)
            }
        }
    }
}

interface EventClick{
    fun clickOpenDetailProduct(idProduct: Int)

    fun clickOpenReview(idOrderItem: Int)
}