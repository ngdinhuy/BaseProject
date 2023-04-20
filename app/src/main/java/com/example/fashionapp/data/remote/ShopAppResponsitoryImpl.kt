package com.example.shopapp.data.remote


import com.example.fashionapp.data.remote.response.LoginResponse
import com.example.fashionapp.model.Product
import java.util.*
import javax.inject.Inject

class ShopAppResponsitoryImpl @Inject constructor(
    private val apiService: ApiService
) : ShopAppResponsitory {
    override suspend fun getAllProducts() {
    }

    override suspend fun login(username: String, password: String): LoginResponse {
        return apiService.login(username, password)
    }

    override suspend fun getProductsByCategory(category: String): List<Product> {
        return apiService.getProductByCategory(category)
    }
}