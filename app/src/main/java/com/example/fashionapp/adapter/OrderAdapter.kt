package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.databinding.ItemOrderBinding
import com.example.fashionapp.model.BillModel
import com.example.fashionapp.model.OrderModel

class OrderAdapter(val context: Context,
                   var listBill : List<OrderModel>,
                    val onItemClick: (orderModel:OrderModel) -> Unit)
    : RecyclerView.Adapter<OrderAdapter.ViewHolder>(){


    inner class ViewHolder(val databinding: ItemOrderBinding ):RecyclerView.ViewHolder(databinding.root){
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val databinding = ItemOrderBinding.inflate(LayoutInflater.from(context), parent, false).apply {
        }
        return ViewHolder(databinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databinding.apply {
            order = listBill[position]
            root.setOnClickListener {
                onItemClick(listBill[position])
            }
        }
    }

    override fun getItemCount(): Int = listBill.size
}