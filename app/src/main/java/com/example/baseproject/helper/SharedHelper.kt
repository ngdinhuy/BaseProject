package com.example.baseproject.helper

import android.content.Context

class SharedHelper (val context: Context) {

    private val shared = context.getSharedPreferences("App_Data", Context.MODE_PRIVATE)
    private val editor = shared.edit()

    fun getBooleanData(key: String, default: Boolean = false): Boolean {
        return shared.getBoolean(key, default)
    }

    fun setBooleanData(key: String, value: Boolean) {
        editor.putBoolean(key, value).commit()
    }

    fun getStringData(key: String, default: String = ""): String {
        return shared.getString(key, default) ?: ""
    }

    fun setStringData(key: String, value: String) {
        editor.putString(key, value).commit()
    }

    fun getIntData(key: String, default: Int = 0): Int {
        return shared.getInt(key, default)
    }

    fun setIntData(key: String, value: Int) {
        editor.putInt(key, value).commit()
    }

    fun getLongData(key: String, default: Long = 0): Long {
        return shared.getLong(key, default)
    }

    fun setLongData(key: String, value: Long) {
        editor.putLong(key, value).commit()
    }

    fun getFloatData(key: String, default: Float = 0f): Float {
        return shared.getFloat(key, default)
    }

    fun setFloatData(key: String, value: Float) {
        editor.putFloat(key, value).commit()
    }

    companion object {
        private const val PARAM_TOKEN = "token"
        private const val PARAM_ID = "id"
        private const val PARAM_USERNAME = "USERNAME"
        private const val PARAM_ROLE = "ROLE"
    }


}