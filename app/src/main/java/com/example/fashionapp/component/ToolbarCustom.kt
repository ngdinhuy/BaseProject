package com.example.fashionapp.component

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.example.fashionapp.R

class ToolbarCustom(
    context: Context,
    attributeSet: AttributeSet
) : Toolbar(context, attributeSet) {
    val imgBack : ImageView
    init {
        inflate(context, R.layout.toolbar, this)
        imgBack = findViewById(R.id.back_button)
        imgBack.setOnClickListener{
            setNavigationOnClickListener {
                (context as? Activity)?.onBackPressed()
            }
        }
    }
}