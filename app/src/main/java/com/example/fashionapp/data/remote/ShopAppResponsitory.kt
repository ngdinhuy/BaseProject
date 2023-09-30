package com.example.shopapp.data.remote

import com.example.fashionapp.data.remote.response.BaseResponse
import com.example.fashionapp.data.remote.response.CategoryAndProductResponse
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.model.UserModel
import com.example.fashionapp.model.Product

interface ShopAppResponsitory {
    suspend fun getAllProducts(): BaseResponse<List<Product>>

    suspend fun login(username: String, password: String): BaseResponse<UserModel>

    suspend fun getProductsByCategory(idCategory: Int, filter:Int):BaseResponse<List<Product>>

    suspend fun getAllCategories(): BaseResponse<List<CategoryModel>>

    suspend fun getCategoryAndProduct(): BaseResponse<List<CategoryAndProductResponse>>

}