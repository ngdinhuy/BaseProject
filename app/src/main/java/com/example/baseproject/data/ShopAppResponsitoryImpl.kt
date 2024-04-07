package com.example.baseproject.data


import com.example.baseproject.data.ApiService
import com.example.baseproject.data.ShopAppResponsitory
import javax.inject.Inject

class ShopAppResponsitoryImpl @Inject constructor(
    private val apiService: ApiService
) : ShopAppResponsitory {
    override suspend fun getAllProducts() {
    }
}