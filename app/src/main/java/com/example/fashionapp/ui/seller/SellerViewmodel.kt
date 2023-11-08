package com.example.fashionapp.ui.seller

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionapp.model.Product
import com.example.fashionapp.utils.Event
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SellerViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
): ViewModel() {
    val goToDetailScreenEvent = MutableLiveData<Event<Product>>()
    val goToAddProductEvent = MutableLiveData<Event<Unit>>()
    val goToSplashEvent = MutableLiveData<Event<Unit>>()
    val goToSettingEvent = MutableLiveData<Event<Unit>>()
    val goToChatListEvent = MutableLiveData<Event<Unit>>()

    fun goToDetailScreen(product: Product){
        goToDetailScreenEvent.value = Event(product)
    }

    fun goToAddProduct(){
        goToAddProductEvent.value = Event(Unit)
    }

    fun logout(){
        goToSplashEvent.value = Event(Unit)
    }

    fun goToSetting(){
        goToSettingEvent.value = Event(Unit)
    }

    fun goToChatList(){
        goToChatListEvent.value = Event(Unit)
    }
}