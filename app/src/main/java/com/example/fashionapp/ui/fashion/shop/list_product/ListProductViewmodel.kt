package com.example.fashionapp.ui.fashion.shop.list_product

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.model.Product
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    val responsitoryImpl: ShopAppResponsitoryImpl
) : ViewModel(){
    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading : LiveData<Event<Boolean>> = _isLoading
    val listProduct = MutableLiveData<List<Product>>()
    private val _backEvent = MutableLiveData<Event<Unit>>()
    val backEvent : LiveData<Event<Unit>> = _backEvent

    fun getProductInCategory(idCategory: Int){
        viewModelScope.launch {
            responsitoryImpl.getProductsByCategory(idCategory,0).apply {
                if (this.errors.isEmpty() && this.dataResponse != null){
                    listProduct.value = this.dataResponse!!
                } else {
                    context.makeToast(this.errors[0])
                }

            }
        }
    }
    fun backToPreviousScreen(){
        _backEvent.value = Event(Unit)
    }
}