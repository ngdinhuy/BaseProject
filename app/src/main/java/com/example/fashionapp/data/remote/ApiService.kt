package com.example.shopapp.data.remote

import retrofit2.http.*
import java.util.*

interface ApiService {
    @GET("bai1/customers")
    suspend fun getAllProducts()
}