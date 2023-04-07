package com.example.shopapp.data.remote

import java.util.*

interface ShopAppResponsitory {
    suspend fun getAllProducts()
}