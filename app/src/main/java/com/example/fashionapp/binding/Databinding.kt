package com.example.fashionapp.binding

import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.example.fashionapp.Define
import com.example.fashionapp.R
import java.io.File

object Databinding {
    @BindingAdapter("app:loadImage", "app:isCacheUrl", requireAll = false)
    @JvmStatic
    fun loadImage(view: View, url: String?, isCacheUrl: Boolean?) {
        if (url.isNullOrEmpty()) {
            return
        }
        Log.e("url", url.toString())
        if (url.contains(Define.HTTP)) {
            val option = RequestOptions()
                .centerCrop()
                .signature(ObjectKey(System.currentTimeMillis().toString()))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_launcher_background)
//            if (isCacheUrl == true) {
//                option.signature(ObjectKey(System.currentTimeMillis().toString()))
//                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//            }
            Glide.with(view.context).asBitmap().load(url).apply(option).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    (view as ImageView).setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    (view as ImageView).setImageDrawable(placeholder)
                }
            })
        } else {
            Glide.with(view.context)
                .load(File(url))
                .into(view as ImageView)
        }

    }

    @BindingAdapter("app:isVisible")
    @JvmStatic
    fun View.isVisible(isVisible: Boolean) {
        if (isVisible) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }

    @BindingAdapter("app:selected")
    @JvmStatic
    fun TextView.isSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }

    @BindingAdapter("app:isBold")
    @JvmStatic
    fun TextView.isBold(isBold: Boolean) {
        if (isBold) {
            this.setTypeface(null, Typeface.BOLD)
        } else {
            this.setTypeface(null, Typeface.NORMAL)
        }
    }
}