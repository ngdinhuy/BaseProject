package com.example.baseproject.utils

import android.content.Context
import android.content.res.Configuration
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun formatMillisToVietnameseTime(millis: Long): String {
        val localeVN = Locale("vi", "VN")
        val date = Date(millis)

        val formatter = SimpleDateFormat("HH:mm || dd, 'thg' M, yyyy", localeVN)
        return formatter.format(date)
    }

    fun checkSystemIsDarkMode(context: Context) : Boolean{
        val nightModeFlags: Int = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }
}