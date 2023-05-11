package com.example.fashionapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.Display

class Prefs {
    fun setToken(token:String){
        Log.e("editor", editor.toString())
        editor?.putString(PARAM_TOKEN,token)?.apply()
    }

    fun getToken(): String? = shared?.getString(PARAM_TOKEN,"")

    companion object{
        const val PARAM_TOKEN = "token"
        var shared: SharedPreferences? = null
        var editor : SharedPreferences.Editor? = null
        fun newInstance(context: Context): Prefs{
            if (shared == null){
                shared = context.getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE)
                editor = shared?.edit()
            }
            return Prefs()
        }
    }


}