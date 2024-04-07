package com.example.baseproject.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs {
    fun setToken(token: String) {
        editor?.putString(PARAM_TOKEN, token)?.apply()
    }

    fun getToken(): String = shared?.getString(PARAM_TOKEN, "") ?: ""

    fun setUsername(username: String) {
        editor?.putString(PARAM_USERNAME, username)?.apply()
    }

    fun getUsername(): String = shared?.getString(PARAM_USERNAME, "") ?: ""

    fun setId(id: Int) {
        editor?.putInt(PARAM_ID, id)?.apply()
    }

    fun getId(): Int = shared?.getInt(PARAM_ID, 0) ?: 0

    fun setRole(role: Int) {
        editor?.putInt(PARAM_ROLE, role)?.apply()
    }

    fun getRole(): Int = shared?.getInt(PARAM_ROLE, -1) ?: -1

    companion object {
        private const val PARAM_TOKEN = "token"
        private const val PARAM_ID = "id"
        private const val PARAM_USERNAME = "USERNAME"
        private const val PARAM_ROLE = "ROLE"
        var shared: SharedPreferences? = null
        var editor: SharedPreferences.Editor? = null
        fun newInstance(context: Context): Prefs {
            if (shared == null) {
                shared = context.getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE)
                editor = shared?.edit()
            }
            return Prefs()
        }
    }


}