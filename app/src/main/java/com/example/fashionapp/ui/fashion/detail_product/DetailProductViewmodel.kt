package com.example.fashionapp.ui.fashion.detail_product

import android.content.Context
import android.media.metrics.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DetailProductViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    val shopAppResponsitoryImpl: ShopAppResponsitoryImpl
): ViewModel() {
    private val _amount = MutableLiveData<String>("0")
    val amount : LiveData<String> = _amount
    private val _eventBack = MutableLiveData<com.example.fashionapp.utils.Event<Unit>>()
    val eventBack : LiveData<com.example.fashionapp.utils.Event<Unit>> = _eventBack

    fun addAmount(){
        val mount = amount.value!!.toInt()
        _amount.value = (mount + 1).toString()
    }

    fun subtractAmount(){
        val mount = amount.value!!.toInt()
        if (mount == 0){

        }else{
            _amount.value = (mount - 1).toString()
        }
    }

    fun backEvent(){
        _eventBack.value = com.example.fashionapp.utils.Event(Unit)
    }
}