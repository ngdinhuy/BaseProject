package com.example.fashionapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.Display

class Prefs {
    companion object{
        const val PARAM_TOKEN = "token"
        var shared: SharedPreferences? = null
        var editor : SharedPreferences.Editor? = null

        fun setToken(token:String){
            editor?.putString(PARAM_TOKEN,token)
        }

        fun getToken(): String? = shared?.getString(PARAM_TOKEN,"")

        fun newInstance(context: Context): SharedPreferences?{
            if (shared == null){
                shared = context.getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE)
                editor = shared?.edit()
            }
            return shared
        }
    }


}