package com.example.shopapp.data.remote

import com.example.fashionapp.data.remote.request.LoginRequest
import com.example.fashionapp.data.remote.response.BaseResponse
import com.example.fashionapp.data.remote.response.LoginResponse
import com.example.fashionapp.model.Product
import retrofit2.http.*
import java.util.*

interface ApiService {
    @GET("products")
    suspend fun getAllProducts() : List<Product>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): BaseResponse<LoginResponse>

    @GET("products/category/{cate}")
    suspend fun getProductByCategory(
        @Path("cate") category: String
    ): List<Product>

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>
}