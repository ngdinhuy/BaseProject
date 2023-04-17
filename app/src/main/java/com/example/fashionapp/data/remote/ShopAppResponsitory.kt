package com.example.shopapp.data.remote

import com.example.fashionapp.data.remote.response.LoginResponse
import java.util.*

interface ShopAppResponsitory {
    suspend fun getAllProducts()

    suspend fun login(username: String, password: String): LoginResponse
}