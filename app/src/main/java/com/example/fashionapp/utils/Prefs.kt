package com.example.fashionapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.Display

class Prefs {
    fun setToken(token:String){
        editor?.putString(PARAM_TOKEN,token)?.apply()
    }

    fun getToken(): String? = shared?.getString(PARAM_TOKEN,"")

    fun setUsername(username: String){
        editor?.putString(PARAM_USERNAME,username)?.apply()
    }

    fun getUsername(): String? = shared?.getString(PARAM_USERNAME,"")
    companion object{
        const val PARAM_TOKEN = "token"
        const val PARAM_USERNAME = "USERNAME"
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