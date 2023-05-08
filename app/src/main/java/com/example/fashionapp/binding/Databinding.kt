package com.example.fashionapp.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fashionapp.R

object Databinding {
    @BindingAdapter("app:loadImage")
    @JvmStatic
    fun loadImage(view: View, url: String?){
        if (url.isNullOrEmpty()) {
            return
        }
        Log.e("url", url.toString())
        val option = RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background)
        Glide.with(view.context).load(url).apply(option).into(view as ImageView)
    }
}