package com.example.shopapp.data.remote

import androidx.compose.ui.unit.DpOffset
import com.example.fashionapp.data.remote.request.LoginRequest
import com.example.fashionapp.data.remote.request.RequestProduct
import com.example.fashionapp.data.remote.request.SignUpRequest
import com.example.fashionapp.data.remote.request.UpdateUserInfoRequest
import com.example.fashionapp.data.remote.response.*
import com.example.fashionapp.model.*
import okhttp3.MultipartBody
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

interface ApiService {
    @GET("product/all")
    suspend fun getAllProducts(): BaseResponse<List<Product>>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): BaseResponse<UserModel>

    @GET("product/category")
    suspend fun getProductByCategory(
        @Query("id") idCategory: Int,
        @Query("filter") filter: Int
    ): BaseResponse<List<Product>>

    @GET("category/all")
    suspend fun getAllCategories(): BaseResponse<List<CategoryModel>>

    @GET("product/category_product")
    suspend fun getCategoryAndProduct(): BaseResponse<List<CategoryAndProductResponse>>

    @POST("product/purchase")
    @FormUrlEncoded
    suspend fun addToCart(
        @Field("idUser") idUser: Int,
        @Field("idProduct") idProduct: Int,
        @Field("quantity") quantity: Int
    ): BaseResponse<CartResponse>

    @GET("cart/all/{id}")
    suspend fun getAllCart(
        @Path("id") id: Int
    ): BaseResponse<List<CartResponse>>

    @POST("cart/update_quantity")
    @FormUrlEncoded
    suspend fun editCartItem(
        @Field("id_cart_item") idCartItem: Int,
        @Field("quantity_change") quantityChange: Int,
        @Field("id_user") idUser: Int
    ): BaseResponse<List<CartResponse>>

    @DELETE("cart")
    suspend fun deleteCartItem(
        @Query("id_cart_item") idCartItem: Int,
        @Query("id_user") idUser: Int
    ): BaseResponse<List<CartResponse>>

    @GET("user/{id}")
    suspend fun getInfoUser(
        @Path("id") idUser: Int
    ): BaseResponse<UserInfoResponse>

    @GET("order/all")
    suspend fun getAllUserOrder(
        @Query("id_user") idUser: Int
    ): BaseResponse<List<OrderModel>>

    @GET("order/detail")
    suspend fun getAllOrderItem(
        @Query("id_order") idOrder: Int
    ): BaseResponse<List<OrderItemDetail>>

    @PUT("user/{id}")
    suspend fun updateInfoUser(
        @Path("id") idUser: Int,
        @Body updateUserInfo: UpdateUserInfoRequest
    ): BaseResponse<UserInfoResponse>

    @POST("auth/change_password")
    @FormUrlEncoded
    suspend fun updatePassword(
        @Field("id_user") idUser: Int,
        @Field("new_password") newPassword: String,
        @Field("old_password") oldPassword: String
    ): BaseResponse<UserInfoResponse>

    @POST("auth/register")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): BaseResponse<UserModel>

    @Multipart
    @POST("user/change_avatar/{id}")
    suspend fun changeAvatar(
        @Path("id") id: Int,
        @Part multipartFile: MultipartBody.Part
    ): BaseResponse<UserModel>

    @GET("product/seller/{id}")
    suspend fun getListProductBySellerId(
        @Path("id") idSeller: Int
    ): BaseResponse<kotlin.collections.List<Product>>

    @Multipart
    @POST("product/insert")
    suspend fun insertProduct(
        @Part("requestProduct") requestProduct: RequestProduct,
        @Part multipartFiles: List<MultipartBody.Part>
    ): BaseResponse<Any>

    @POST("order/insert")
    @FormUrlEncoded
    suspend fun checkouut(
        @Field("idUser") idUser: Int,
        @Field("state_payment") statePayment: Int
    ): BaseResponse<Any>

    @GET("statistic/monthly_income")
    suspend fun getStatisMonthly(
        @Query("idSeller") idSeller: Int
    ): BaseResponse<Map<String, Double>>

    @GET("statistic/statistic_current_month")
    suspend fun getCurrentStatistic(
        @Query("idSeller") idSeller: Int,
        @Query("type") type: Int
    ): BaseResponse<Double>

    @GET("product/detail")
    suspend fun getProductById(
        @Query("id") id: Int
    ): BaseResponse<Product>

    @GET("chat/list/{id}")
    suspend fun getChatList(
        @Path("id") id: Int
    ): BaseResponse<List<ChatListResponse>>

    @GET("chat/message")
    suspend fun getChatDetail(
        @Query("id_user") idSender: Int,
        @Query("id_partner") idReceiver: Int,
        @Query("offset") offset: Int
    ): PagingBaseResponse<ArrayList<ChatDetailResponse>>

    @GET("user/withdraw")
    suspend fun sellerWithdraw(
        @Query("id_user") idUser: Int,
        @Query("money") money: Double,
        @Query("mail_paypal") mailPaypal: String,
        @Query("password") password: String,
        @Query("is_save_mail_paypal") isSaveMailPaypal: Boolean
    ): BaseResponse<UserInfoResponse>

    @GET("product/seller_category_product")
    suspend fun getSellerCategoryProduct(
        @Query("idUser") idUser: Int
    ): BaseResponse<List<CategoryAndProductResponse>>

    @GET("order_item/detail")
    suspend fun getOrderItemDetail(
        @Query("id") idOrderItem: Int
    ): BaseResponse<OrderItemDetail>

    @FormUrlEncoded
    @POST("product/rate")
    suspend fun rateProduct(
        @Field("id_order_item") idOrderItem: Int,
        @Field("rate") rate: Int
    ): BaseResponse<OrderItemDetail>
}