package com.example.fashionapp.ui.seller.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.StatisticType
import com.example.fashionapp.data.remote.response.UserInfoResponse
import com.example.fashionapp.model.UserModel
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.Utils
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
    var statistic = MutableLiveData<MutableMap<String, Double>>()
    var statisticIncome = MutableLiveData<String>()
    var statisticOrder = MutableLiveData<String>()
    val statisticRate = MutableLiveData<String>()
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

    fun getStatisticIncome(){
        val idUser = Prefs.newInstance(context).getId()
        viewModelScope.launch {
            responsitoryImpl.getStatisticMonthly(idUser).apply {
                if (errors.isEmpty()){
                    statistic.value = Utils.getMapStatistic(dataResponse)
                    statisticIncome.value = "${(dataResponse[Utils.getCurrentMonth()?:""] ?: 0)}$"
                } else{
                    context.makeToast(errors[0])
                }
            }
        }
    }

    fun getStatCurrentMonth(){
        val idUser = Prefs.newInstance(context).getId();
        viewModelScope.launch {
            responsitoryImpl.getCurrentStatistic(idUser, StatisticType.ORDER_CURRENT_MONTH).apply {
                if (errors.isEmpty()){
                    statisticOrder.value = dataResponse.toInt().toString()
                } else {
                    context.makeToast(errors[0])
                }
            }

            responsitoryImpl.getCurrentStatistic(idUser, StatisticType.RATE_CURRENT_MONTH).apply {
                if (errors.isEmpty()){
                    statisticRate.value = dataResponse.toString()
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }
}