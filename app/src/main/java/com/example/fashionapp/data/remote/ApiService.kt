package com.example.shopapp.data.remote

import com.example.fashionapp.data.remote.response.LoginResponse
import retrofit2.http.*
import java.util.*

interface ApiService {
    @GET("bai1/customers")
    suspend fun getAllProducts()

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field(value = "username") username: String,
        @Field(value = "password") password: String
    ): LoginResponse
}