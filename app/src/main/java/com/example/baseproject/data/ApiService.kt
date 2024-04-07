package com.example.baseproject.data

import retrofit2.http.*
import java.util.*

interface ApiService {
    @GET("bai1/customers")
    suspend fun getAllProducts()
}