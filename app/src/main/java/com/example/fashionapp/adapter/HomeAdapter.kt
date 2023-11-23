package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionapp.Define
import com.example.fashionapp.data.remote.response.CategoryAndProductResponse
import com.example.fashionapp.databinding.ItemRvHomeBinding
import com.example.fashionapp.model.Product

class HomeAdapter(
    val context: Context,
    val listInfo: List<CategoryAndProductResponse>,
    val event : HomeItemListAdapter.GoToDetailEvent,
    val isShowViewAll: Boolean? = true,
    ): RecyclerView.Adapter<HomeAdapter.HomeItemViewHolder>() {
    var adapterHomeItemListAdapter : HomeItemListAdapter? = null

    class HomeItemViewHolder(val binding: ItemRvHomeBinding)
        :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val binding = ItemRvHomeBinding.inflate(LayoutInflater.from(context), parent, false)
        return HomeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.binding.apply {
//            if (position == 0){
//                title = "MEN"
//                quotes = Define.MENS_QUOTES
//                adapterHomeItemListAdapter = HomeItemListAdapter(context, map[Define.MEN_CLOTHES] as ArrayList<Product>)
//                adapterHomeItemListAdapter?.goToDetailEvent = event
//                rvItem.apply {
//                    adapter = adapterHomeItemListAdapter
//                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                }
//            }else{
//                title = "WOMEN"
//                quotes = Define.WOMEN_QUOTES
//                adapterHomeItemListAdapter = HomeItemListAdapter(context, map[Define.WOMEN_CLOTHES] as ArrayList<Product>)
//                adapterHomeItemListAdapter?.goToDetailEvent = event
//                rvItem.apply {
//                    adapter = adapterHomeItemListAdapter
//                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                }
//            }
            title = listInfo[position].titleCategory
            quotes = listInfo[position].descriptionCategory
            adapterHomeItemListAdapter = HomeItemListAdapter(context, listInfo[position].products as ArrayList<Product>)
            adapterHomeItemListAdapter?.goToDetailEvent = event
            tvViewAll.isVisible = isShowViewAll ?: true
            rvItem.apply {
                adapter = adapterHomeItemListAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun getItemCount(): Int = listInfo.size
}