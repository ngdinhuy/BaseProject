package com.example.baseproject.ui.auth.images

import android.content.ContentUris
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.baseproject.databinding.ItemImageBinding

class ImageAdapter() : PagingDataAdapter<Uri, ImageAdapter.ImageViewHolder>
    (object : DiffUtil.ItemCallback<Uri>() {
    override fun areItemsTheSame(
        oldItem: Uri,
        newItem: Uri
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Uri,
        newItem: Uri
    ): Boolean {
        return oldItem == newItem
    }
}) {

        var clickListener: ClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int
    ) {
        holder.bindData(getItem(position))
    }

    inner class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(position)?.let {
                    clickListener?.onClickListener(ContentUris.parseId(it))
                }
            }
        }

        fun bindData(uri: Uri?) {
            uri?.let {
                val option =
                    RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                Glide.with(itemView.context)
                    .load(it)
                    .apply(option)
                    .into(binding.ivImage)
            }
        }

    }
}

interface ClickListener {
    fun onClickListener(id: Long)
}