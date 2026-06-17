package com.example.baseproject.data

import java.util.*

interface ShopAppResponsitory {
    suspend fun getAllProducts()

    suspend fun getConfig(): String
}