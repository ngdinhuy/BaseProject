package com.example.fashionapp.utils

import android.content.Context
import android.widget.Toast

class Utils {
}
fun Context.makeToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}