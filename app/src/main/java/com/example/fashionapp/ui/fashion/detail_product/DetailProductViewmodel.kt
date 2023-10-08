package com.example.fashionapp.ui.fashion.detail_product

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.local.MyResponsitory
import com.example.fashionapp.model.Product
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProductViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    val shopAppResponsitoryImpl: ShopAppResponsitoryImpl,
    val myResponsitory: MyResponsitory
) : ViewModel() {
    private val _amount = MutableLiveData<String>("0")
    val amount: LiveData<String> = _amount
    private val _eventBack = MutableLiveData<com.example.fashionapp.utils.Event<Unit>>()
    val eventBack: LiveData<com.example.fashionapp.utils.Event<Unit>> = _eventBack
    private val _likeEvent = MutableLiveData<com.example.fashionapp.utils.Event<Boolean>>()
    val likeEvent: MutableLiveData<com.example.fashionapp.utils.Event<Boolean>> = _likeEvent
    val isLike = MutableLiveData<Boolean>(false)

    fun addAmount() {
        val mount = amount.value!!.toInt()
        _amount.value = (mount + 1).toString()
    }

    fun subtractAmount() {
        val mount = amount.value!!.toInt()
        if (mount == 0) {

        } else {
            _amount.value = (mount - 1).toString()
        }
    }

    fun backEvent() {
        _eventBack.value = com.example.fashionapp.utils.Event(Unit)
    }

    fun addToCart(product: Product) {
//        viewModelScope.launch(Dispatchers.IO) {
//            if (amount.value != "0") {
//                myResponsitory.addProduct(
//                    product,
//                    Prefs.newInstance(context).getUsername()!!,
//                    amount.value!!.toInt()
//                )
//            } else {
//                myResponsitory.addProduct(
//                    product,
//                    Prefs.newInstance(context).getUsername()!!,
//                    1
//                )
//            }
//        }

        viewModelScope.launch {
            val idUser = Prefs.newInstance(context).getId()
            val response = shopAppResponsitoryImpl.addToCart(
                idUser,
                idProduct = product._id!!,
                if (amount.value != "0") amount.value!!.toInt() else 1
            )
            if (response.errors.isEmpty()){
                context.makeToast("Success")
                backEvent()
            } else {
                context.makeToast(response.errors[0])
            }
        }

    }

    fun likeProduct() {
        if (isLike.value != null && !isLike.value!!) {
            isLike.value = true
            _likeEvent.value = com.example.fashionapp.utils.Event(true)
        } else {
            isLike.value = false
            _likeEvent.value = com.example.fashionapp.utils.Event(false)
        }

    }

}