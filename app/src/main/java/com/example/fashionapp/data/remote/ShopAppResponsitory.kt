package com.example.shopapp.data.remote

import com.example.fashionapp.data.remote.response.LoginResponse
import com.example.fashionapp.model.Product
import java.util.*

interface ShopAppResponsitory {
    suspend fun getAllProducts(): List<Product>

    suspend fun login(username: String, password: String): LoginResponse

    suspend fun getProductsByCategory(category: String):List<Product>

    suspend fun getAllCategories(): List<String>
}