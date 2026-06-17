package com.example.baseproject.data


import android.util.Log
import com.example.baseproject.data.ApiService
import com.example.baseproject.data.ShopAppResponsitory
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ShopAppResponsitoryImpl @Inject constructor(
    private val apiService: ApiService
) : ShopAppResponsitory {
    override suspend fun getAllProducts() {
        Log.e("TestViewmodel", "getAllProducts: ${currentTime()}")
        delay(2000)
        throw Exception("Failed to fetch products")
    }

    override suspend fun getConfig(): String {
        Log.e("TestViewmodel", "getConfig: ${currentTime()}")
        delay(1000)
        return "Config data from repository"
    }

    private fun currentTime(): String =
        SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())
}