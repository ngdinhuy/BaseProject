package com.example.fashionapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.fashionapp.Define
import com.example.fashionapp.R
import com.example.fashionapp.Role
import com.example.fashionapp.databinding.ItemProductListSellerBinding
import com.example.fashionapp.databinding.ItemRvClothingCategoryBinding
import com.example.fashionapp.databinding.ItemShopFragmentBinding
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.model.Product
import com.example.fashionapp.utils.Prefs
import java.util.zip.Inflater

class ShopAdapter(
    val context: Context,
    var list: List<Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var goToDetailEvent: GoToDetailEvent? = null

    class ViewHolder(val binding: ItemRvClothingCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    class SellerViewHoder(val binding: ItemProductListSellerBinding)
        : RecyclerView.ViewHolder(binding.root){}

    override fun getItemViewType(position: Int): Int {
        return Prefs.newInstance(context).getRole()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == Role.SELLER){
            val binding = ItemProductListSellerBinding.inflate(LayoutInflater.from(context), parent, false)
            return SellerViewHoder(binding)
        }
        val binding =
            ItemRvClothingCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Role.SELLER){
            val viewHoder = holder as SellerViewHoder
            viewHoder.binding.product = list[position]
            Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_camera))
                .load(list[position].image?.get(0) ?: "")
                .signature(ObjectKey(System.currentTimeMillis().toString()))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(viewHoder.binding.ivItem)
            viewHoder.binding.clItem.setOnClickListener {
                goToDetailEvent?.goToDetail(list[position])
            }
        } else {
            val viewHoder = holder as ViewHolder
            viewHoder.binding.product = list[position]
            Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_camera))
                .load(list[position].image?.get(0) ?: "")
                .signature(ObjectKey(System.currentTimeMillis().toString()))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(viewHoder.binding.ivItem)
            viewHoder.binding.clItem.setOnClickListener {
                goToDetailEvent?.goToDetail(list[position])
            }
        }

    }

    override fun getItemCount(): Int = list.size

    interface GoToDetailEvent {
        fun goToDetail(product: Product)
    }
}