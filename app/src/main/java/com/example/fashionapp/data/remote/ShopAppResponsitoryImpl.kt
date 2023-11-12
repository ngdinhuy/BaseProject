package com.example.shopapp.data.remote


import android.net.Uri
import com.example.fashionapp.data.remote.request.LoginRequest
import com.example.fashionapp.data.remote.request.RequestProduct
import com.example.fashionapp.data.remote.request.SignUpRequest
import com.example.fashionapp.data.remote.request.UpdateUserInfoRequest
import com.example.fashionapp.data.remote.response.*
import com.example.fashionapp.model.*
import okhttp3.MultipartBody
import vn.zalopay.sdk.analytic.network.http.RequestBody
import java.io.File
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

    override suspend fun updatePassword(
        idUser: Int,
        newPassword: String,
        oldPassword: String
    ): BaseResponse<UserInfoResponse> {
        return apiService.updatePassword(idUser, newPassword, oldPassword)
    }

    override suspend fun signUp(request: SignUpRequest): BaseResponse<UserModel> {
        return apiService.signUp(request)
    }

    override suspend fun changeAvatar(idUser: Int, multipartBody: MultipartBody.Part): BaseResponse<UserModel> {
        return apiService.changeAvatar(idUser, multipartBody)
    }

    override suspend fun getListProductBySellerId(idUser: Int): BaseResponse<List<Product>> {
        return apiService.getListProductBySellerId(idUser)
    }

    override suspend fun insertProduct(request: RequestProduct, list: List<MultipartBody.Part>): BaseResponse<Any> {
        return apiService.insertProduct(request, list)
    }

    override suspend fun checkout(idUser: Int, statePayment: Int): BaseResponse<Any> {
        return apiService.checkouut(idUser, statePayment)
    }

    override suspend fun getStatisticMonthly(idSeller: Int): BaseResponse<Map<String, Double>> {
        return apiService.getStatisMonthly(idSeller)
    }

    override suspend fun getCurrentStatistic(idSeller: Int, type: Int): BaseResponse<Double> {
        return apiService.getCurrentStatistic(idSeller, type)
    }

    override suspend fun getProductById(id: Int): BaseResponse<Product> {
        return apiService.getProductById(id)
    }

    override suspend fun getChatList(id: Int): BaseResponse<List<ChatListResponse>> {
        return apiService.getChatList(id)
    }

    override suspend fun getChatListDetail(idUser: Int, idPartner: Int, offset: Int): PagingBaseResponse<ArrayList<ChatDetailResponse>> {
        return apiService.getChatDetail(idUser, idPartner, offset)
    }

    override suspend fun sellerWithdraw(
        idUser: Int,
        money: Double,
        mailPaypal: String,
        password: String,
        isSaveMailPaypal: Boolean
    ): BaseResponse<UserInfoResponse> {
        return apiService.sellerWithdraw(idUser, money, mailPaypal, password,isSaveMailPaypal)
    }
}