package com.example.shopapp.data.remote

import com.example.fashionapp.data.remote.response.LoginResponse
import com.example.fashionapp.model.Product
import retrofit2.http.*
import java.util.*

interface ApiService {
    @GET("bai1/customers")
    suspend fun getAllProducts() : List<Product>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field(value = "username") username: String,
        @Field(value = "password") password: String
    ): LoginResponse

    @GET("products/category/{cate}")
    suspend fun getProductByCategory(
        @Path("cate") category: String
    ): List<Product>

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>
}