package com.example.shopapp.data.remote

import android.net.Uri
import androidx.room.Index.Order
import com.example.fashionapp.data.remote.request.RequestProduct
import com.example.fashionapp.data.remote.request.SignUpRequest
import com.example.fashionapp.data.remote.request.UpdateUserInfoRequest
import com.example.fashionapp.data.remote.response.*
import com.example.fashionapp.model.*
import okhttp3.MultipartBody

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

    suspend fun getUserOrder(
        idUser: Int
    ): BaseResponse<List<OrderModel>>

    suspend fun getAllOrderItem(
        idOrder: Int
    ): BaseResponse<List<OrderItemDetail>>

    suspend fun updateInfoUser(
        idUser: Int,
        request: UpdateUserInfoRequest
    ): BaseResponse<UserInfoResponse>

    suspend fun updatePassword(
        idUser: Int,
        newPassword: String,
        oldPassword: String
    ): BaseResponse<UserInfoResponse>

    suspend fun signUp(
        request: SignUpRequest
    ): BaseResponse<UserModel>

    suspend fun changeAvatar(idUser: Int, multipartBody: MultipartBody.Part): BaseResponse<UserModel>

    suspend fun getListProductBySellerId(idUser: Int): BaseResponse<List<Product>>

    suspend fun insertProduct(request: RequestProduct, list: List<MultipartBody.Part>): BaseResponse<Any>

    suspend fun checkout(idUser: Int): BaseResponse<Any>

    suspend fun getStatisticMonthly(idSeller: Int): BaseResponse<Map<String, Double>>

    suspend fun getCurrentStatistic(idSeller: Int, type: Int): BaseResponse<Double>

    suspend fun getProductById(id: Int): BaseResponse<Product>

    suspend fun getChatList(id:Int): BaseResponse<List<ChatListResponse>>

    suspend fun getChatListDetail(
        idUser: Int,
        idPartner: Int,
        offset: Int
    ): PagingBaseResponse<ArrayList<ChatDetailResponse>>
}