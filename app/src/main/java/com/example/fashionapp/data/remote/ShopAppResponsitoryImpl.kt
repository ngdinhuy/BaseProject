package com.example.shopapp.data.remote


import com.example.fashionapp.data.remote.response.LoginResponse
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
}