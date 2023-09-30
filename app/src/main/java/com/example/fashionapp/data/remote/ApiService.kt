package com.example.shopapp.data.remote

import com.example.fashionapp.data.remote.request.LoginRequest
import com.example.fashionapp.data.remote.response.BaseResponse
import com.example.fashionapp.data.remote.response.CategoryAndProductResponse
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.model.UserModel
import com.example.fashionapp.model.Product
import retrofit2.http.*
import java.util.*

interface ApiService {
    @GET("product/all")
    suspend fun getAllProducts() : BaseResponse<List<Product>>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): BaseResponse<UserModel>

    @GET("product/category/{cate}")
    suspend fun getProductByCategory(
        @Path("cate") idCategory: Int
    ): BaseResponse<List<Product>>

    @GET("category/all")
    suspend fun getAllCategories(): BaseResponse<List<CategoryModel>>

    @GET("product/category_product")
    suspend fun getCategoryAndProduct(): BaseResponse<List<CategoryAndProductResponse>>

}