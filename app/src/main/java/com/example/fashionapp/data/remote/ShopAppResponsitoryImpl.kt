package com.example.shopapp.data.remote


import com.example.fashionapp.data.remote.request.LoginRequest
import com.example.fashionapp.data.remote.response.BaseResponse
import com.example.fashionapp.data.remote.response.CategoryAndProductResponse
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.model.UserModel
import com.example.fashionapp.model.Product
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

}