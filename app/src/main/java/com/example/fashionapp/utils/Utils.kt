package com.example.fashionapp.utils

import android.content.Context
import android.widget.Toast
import com.google.android.material.chip.ChipGroup

class Utils {
}
fun Context.makeToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Double.get2digit(): Double{
    return String.format(("%.2f"), this).toDouble()
}