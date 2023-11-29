package com.example.fashionapp.ui.seller.list_order

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.remote.response.OrderItemSellerResponse
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOrderSellerViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    val impl: ShopAppResponsitoryImpl
): ViewModel() {
    val listOrderItem = MutableLiveData<List<OrderItemSellerResponse>>()

    fun loadListOrderItem(){
        val idUser = Prefs.newInstance(context).getId()
        viewModelScope.launch {
            impl.getListOrderItem(idUser).apply {
                if (errors.isEmpty()){
                    listOrderItem.value = dataResponse ?: listOf<OrderItemSellerResponse>()
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }
}