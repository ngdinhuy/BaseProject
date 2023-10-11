package com.example.shopapp.data.remote

import com.example.fashionapp.data.remote.response.CartResponse
import com.example.fashionapp.data.remote.response.BaseResponse
import com.example.fashionapp.data.remote.response.CategoryAndProductResponse
import com.example.fashionapp.data.remote.response.UserInfoResponse
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.model.UserModel
import com.example.fashionapp.model.Product

interface ShopAppResponsitory {
    suspend fun getAllProducts(): BaseResponse<List<Product>>

    suspend fun login(username: String, password: String): BaseResponse<UserModel>

    suspend fun getProductsByCategory(idCategory: Int, filter: Int): BaseResponse<List<Product>>

    suspend fun getAllCategories(): BaseResponse<List<CategoryModel>>

    suspend fun getCategoryAndProduct(): BaseResponse<List<CategoryAndProductResponse>>

    suspend fun addToCart(idUser: Int, idProduct: Int, quantity: Int): BaseResponse<CartResponse>

    suspend fun getAllCart(id: Int): BaseResponse<List<CartResponse>>

    suspend fun updateCart(
        idCartItem: Int,
        quantityChange: Int,
        idUser: Int
    ): BaseResponse<List<CartResponse>>

    suspend fun deleteCartItem(
        idCartItem: Int,
        idUser: Int
    ): BaseResponse<List<CartResponse>>

    suspend fun getUserInfo(
        idUser: Int
    ): BaseResponse<UserInfoResponse>
}