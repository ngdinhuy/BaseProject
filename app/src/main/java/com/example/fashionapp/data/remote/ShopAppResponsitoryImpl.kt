package com.example.shopapp.data.remote


import com.example.fashionapp.data.remote.request.LoginRequest
import com.example.fashionapp.data.remote.request.SignUpRequest
import com.example.fashionapp.data.remote.request.UpdateUserInfoRequest
import com.example.fashionapp.data.remote.response.CartResponse
import com.example.fashionapp.data.remote.response.BaseResponse
import com.example.fashionapp.data.remote.response.CategoryAndProductResponse
import com.example.fashionapp.data.remote.response.UserInfoResponse
import com.example.fashionapp.model.*
import javax.inject.Inject

class ShopAppResponsitoryImpl @Inject constructor(
    private val apiService: ApiService
) : ShopAppResponsitory {
    override suspend fun getAllProducts(): BaseResponse<List<Product>> {
        return apiService.getAllProducts()
    }

    override suspend fun login(username: String, password: String): BaseResponse<UserModel> {
        return apiService.login(LoginRequest(username, password))
    }

    override suspend fun getProductsByCategory(
        idCategory: Int,
        filter: Int
    ): BaseResponse<List<Product>> {
        return apiService.getProductByCategory(idCategory, filter)
    }

    override suspend fun getAllCategories(): BaseResponse<List<CategoryModel>> {
        return apiService.getAllCategories()
    }

    override suspend fun getCategoryAndProduct(): BaseResponse<List<CategoryAndProductResponse>> {
        return apiService.getCategoryAndProduct()
    }

    override suspend fun addToCart(
        idUser: Int,
        idProduct: Int,
        quantity: Int
    ): BaseResponse<CartResponse> {
        return apiService.addToCart(idUser, idProduct, quantity)
    }

    override suspend fun getAllCart(id: Int): BaseResponse<List<CartResponse>> {
        return apiService.getAllCart(id)
    }

    override suspend fun updateCart(
        idCartItem: Int,
        quantityChange: Int,
        idUser: Int
    ): BaseResponse<List<CartResponse>> {
        return apiService.editCartItem(idCartItem, quantityChange, idUser)
    }

    override suspend fun deleteCartItem(
        idCartItem: Int,
        idUser: Int
    ): BaseResponse<List<CartResponse>> {
        return apiService.deleteCartItem(idCartItem, idUser)
    }

    override suspend fun getUserInfo(idUser: Int): BaseResponse<UserInfoResponse> {
        return apiService.getInfoUser(idUser)
    }

    override suspend fun getUserOrder(idUser: Int): BaseResponse<List<OrderModel>> {
        return apiService.getAllUserOrder(idUser)
    }

    override suspend fun getAllOrderItem(idOrder: Int): BaseResponse<List<OrderItemDetail>> {
        return apiService.getAllOrderItem(idOrder)
    }

    override suspend fun updateInfoUser(idUser: Int, request: UpdateUserInfoRequest): BaseResponse<UserInfoResponse> {
        return apiService.updateInfoUser(idUser, request)
    }

    override suspend fun updatePassword(idUser: Int, newPassword: String, oldPassword: String):BaseResponse<UserInfoResponse> {
        return apiService.updatePassword(idUser, newPassword, oldPassword)
    }

    override suspend fun signUp(request: SignUpRequest): BaseResponse<UserModel> {
        return apiService.signUp(request)
    }
}