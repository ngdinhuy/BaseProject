package com.example.fashionapp.ui.fashion

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.Define
import com.example.fashionapp.model.Product
import com.example.fashionapp.utils.Event
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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
    val _goToListProductEvent = MutableLiveData<Event<String>>()
    val goToListProductEvent : LiveData<Event<String>> = _goToListProductEvent

    fun getAllProduct(){
        viewModelScope.launch {
            Define.listProduct = responsitoryImpl.getAllProducts()
        }
    }
}