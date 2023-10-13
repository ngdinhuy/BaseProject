package com.example.fashionapp.ui.fashion

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.model.Product
import com.example.fashionapp.utils.Event
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class FashionViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext context: Context
): ViewModel() {
    val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading : LiveData<Event<Boolean>> = _isLoading

    val _goToDetailEvent = MutableLiveData<Event<Product>>()
    val goToDetailEvent : LiveData<Event<Product>> = _goToDetailEvent
    val _goToListProductEvent = MutableLiveData<Event<CategoryModel>>()
    val goToListProductEvent : LiveData<Event<CategoryModel>> = _goToListProductEvent
    private val _goToMyOrderEvent = MutableLiveData<Event<Unit>>()
    val goToMyOderEvent : LiveData<Event<Unit>> = _goToMyOrderEvent
    private val _goToSettingEvent = MutableLiveData<Event<Unit>>()
    val goToSettingEvent : LiveData<Event<Unit>> = _goToSettingEvent

    var filter = 0

    private val _logoutEvent = MutableLiveData<Event<Unit>>()
    val logoutEvent : LiveData<Event<Unit>> = _logoutEvent

    fun logout(){
        _logoutEvent.value = Event(Unit)
    }

    fun goToMyOrder(){
        _goToMyOrderEvent.value = Event(Unit)
    }

    fun goToSetting(){
        _goToSettingEvent.value = Event(Unit)
    }
}