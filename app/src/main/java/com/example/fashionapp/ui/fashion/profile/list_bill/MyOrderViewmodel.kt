package com.example.fashionapp.ui.fashion.profile.list_bill

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.local.MyResponsitory
import com.example.fashionapp.model.BillModel
import com.example.fashionapp.model.OrderModel
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrderViewmodel @Inject constructor(
    val shopAppResponsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
): ViewModel() {
    private val _listOrder = MutableLiveData<Event<List<OrderModel>>>()
    val listOrder : LiveData<Event<List<OrderModel>>> = _listOrder
    val list = ArrayList<OrderModel>()

    fun getListOrderFromServer(){
        val idUser = Prefs.newInstance(context).getId();
        viewModelScope.launch {
            shopAppResponsitoryImpl.getUserOrder(idUser).apply {
                if (this.errors.isEmpty()){
                    list.addAll(this.dataResponse)
                    _listOrder.value = Event(list)
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }
}