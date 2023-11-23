package com.example.fashionapp.ui.fashion.review

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.model.OrderItemDetail
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    val impl: ShopAppResponsitoryImpl
): ViewModel() {
    var idOrderItem = 0
    var orderItemDetail = MutableLiveData<OrderItemDetail>()
    var eventBack = MutableLiveData<Event<Unit>>()
    fun loadOrderItem(){
        viewModelScope.launch {
            impl.getOrderItemDetail(idOrderItem).apply {
                if (errors.isEmpty()){
                    orderItemDetail.value = dataResponse ?: OrderItemDetail()
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

    fun clickRate(rate: Int){
        viewModelScope.launch {
            impl.rateProduct(idOrderItem, rate).apply {
                if (errors.isEmpty()){
                    context.makeToast("Rate success")
                    eventBack.value = Event(Unit)
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }
}