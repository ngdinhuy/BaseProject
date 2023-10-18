package com.example.fashionapp.binding

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.fashionapp.Define
import com.example.fashionapp.R
import java.io.File

object Databinding {
    @BindingAdapter("app:loadImage")
    @JvmStatic
    fun loadImage(view: View, url: String?){
        if (url.isNullOrEmpty()) {
            return
        }
        Log.e("url", url.toString())
        if (url.contains(Define.HTTP)){
            val option = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .signature(ObjectKey(System.currentTimeMillis().toString()))
                .placeholder(R.drawable.ic_launcher_background)
            Glide.with(view.context).load(url).apply(option).into(view as ImageView)
        } else {
            Glide.with(view.context)
                .load(File(url))
                .into(view as ImageView)
        }

    }

    @BindingAdapter("app:isVisible")
    @JvmStatic
    fun View.isVisible(isVisible: Boolean){
        if (isVisible){
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }

    @BindingAdapter("app:selected")
    @JvmStatic
    fun TextView.isSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }
}