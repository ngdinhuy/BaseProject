package com.example.fashionapp.ui.fashion.profile.order_detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.model.OrderItemDetail
import com.example.fashionapp.model.OrderModel
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewmodel @Inject constructor(
    val shopAppResponsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {
    val infoOrder = MutableLiveData<OrderModel>()

    val listOrderItem = MutableLiveData<List<OrderItemDetail>>()

    fun getAllItemOrder(){
        viewModelScope.launch {
            infoOrder.value?._id?.let {
                shopAppResponsitoryImpl.getAllOrderItem(it).apply {
                    if (this.errors.isEmpty()){
                        listOrderItem.value = this.dataResponse?: listOf()
                    } else {
                        context.makeToast(errors[0])
                    }
                }
            }
        }
    }
}