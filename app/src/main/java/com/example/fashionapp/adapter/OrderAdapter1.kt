package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.base.BaseAdapterLoadMore
import com.example.fashionapp.databinding.ItemOrderBinding
import com.example.fashionapp.model.BillModel
import com.example.fashionapp.model.OrderModel

class OrderAdapter1 constructor(
    val context: Context,
    val onItemClick: (orderModel: OrderModel) -> Unit
) : BaseAdapterLoadMore<OrderModel>(OrderItemDiffUtil() as DiffUtil.ItemCallback<OrderModel>) {
    override fun onBinViewHolderNomal(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ViewHolder)?.bindData(context, getItem(position))
    }

    override fun onCreateViewHolderNormal(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val databinding = ItemOrderBinding.inflate(LayoutInflater.from(context), parent, false).apply {}
        return ViewHolder(databinding)
    }

    inner class ViewHolder(val databinding: ItemOrderBinding) : RecyclerView.ViewHolder(databinding.root) {
        fun bindData(context: Context, orderModel: OrderModel) {
            databinding.apply {
                order = orderModel
                root.setOnClickListener {
                    onItemClick(orderModel)
                }
            }
        }
    }
}

class OrderItemDiffUtil : DiffUtil.ItemCallback<OrderModel>() {
    override fun areItemsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: OrderModel, newItem: OrderModel): Boolean {
        return false
    }

}