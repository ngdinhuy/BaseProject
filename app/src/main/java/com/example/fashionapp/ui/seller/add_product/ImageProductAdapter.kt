package com.example.fashionapp.ui.seller.add_product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionapp.databinding.ItemListImageBinding
import java.io.File

class ImageProductAdapter(val context: Context,
                          var listImage: ArrayList<String>,
                          val deleteItem: (id:Int)->Unit?)
    : RecyclerView.Adapter<ImageProductAdapter.ViewHolder>() {
    inner class ViewHolder(val itemListImageBinding: ItemListImageBinding): RecyclerView.ViewHolder(itemListImageBinding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val databinding = ItemListImageBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(databinding)
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemListImageBinding.url = listImage[position]
        holder.itemListImageBinding.ivClose.setOnClickListener {
            deleteItem(position)
        }
    }

    fun updateListImage(listImage: ArrayList<String>){
        this.listImage = listImage
        notifyDataSetChanged()
    }
}
