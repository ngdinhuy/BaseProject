package com.example.shopapp.data.remote


import java.util.*
import javax.inject.Inject

class ShopAppResponsitoryImpl @Inject constructor(
    private val apiService: ApiService
) : ShopAppResponsitory {
    override suspend fun getAllProducts() {
    }
}