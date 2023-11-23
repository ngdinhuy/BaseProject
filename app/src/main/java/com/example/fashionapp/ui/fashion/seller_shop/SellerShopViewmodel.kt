package com.example.fashionapp.ui.fashion.seller_shop

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.remote.response.CategoryAndProductResponse
import com.example.fashionapp.data.remote.response.UserInfoResponse
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerShopViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    private val impl: ShopAppResponsitoryImpl
): ViewModel() {
    var idSeller = 0
    val infoCateProduct = MutableLiveData<List<CategoryAndProductResponse>>()
    val infoSeller = MutableLiveData<UserInfoResponse>()

    fun loadInfoSeller(){
        viewModelScope.launch {
            impl.getUserInfo(idSeller).apply {
                if (errors.isEmpty()){
                    infoSeller.value = dataResponse ?: UserInfoResponse()
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

    fun getListCategoryProduct(){
        viewModelScope.launch {
            impl.getSellerCategoryAndProduct(idSeller).apply {
                if (errors.isEmpty()){
                    infoCateProduct.value = dataResponse ?: listOf()
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }
}