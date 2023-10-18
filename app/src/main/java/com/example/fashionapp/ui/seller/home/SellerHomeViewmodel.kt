package com.example.fashionapp.ui.seller.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.remote.response.UserInfoResponse
import com.example.fashionapp.model.UserModel
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SellerHomeViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
): ViewModel() {
    val userInfo = MutableLiveData<UserInfoResponse>()

    fun getInfoUser(){
        val idUser = Prefs.newInstance(context).getId()
        viewModelScope.launch {
            responsitoryImpl.getUserInfo(idUser).apply {
                if (errors.isEmpty()){
                    userInfo.value = dataResponse?: UserInfoResponse()
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }
}