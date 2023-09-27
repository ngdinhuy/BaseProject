package com.example.shopapp.data.remote


import com.example.fashionapp.data.remote.request.LoginRequest
import com.example.fashionapp.data.remote.response.BaseResponse
import com.example.fashionapp.data.remote.response.LoginResponse
import com.example.fashionapp.model.Product
import java.util.*
import javax.inject.Inject

class ShopAppResponsitoryImpl @Inject constructor(
    private val apiService: ApiService
) : ShopAppResponsitory {
    override suspend fun getAllProducts() : List<Product> {
        return apiService.getAllProducts()
    }

    override suspend fun login(username: String, password: String): BaseResponse<LoginResponse> {
        return apiService.login(LoginRequest(username,password))
    }

    override suspend fun getProductsByCategory(category: String): List<Product> {
        return apiService.getProductByCategory(category)
    }

    override suspend fun getAllCategories(): List<String> {
        return apiService.getAllCategories()
    }
}