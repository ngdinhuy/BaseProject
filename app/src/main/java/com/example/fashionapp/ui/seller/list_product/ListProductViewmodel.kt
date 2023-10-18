package com.example.fashionapp.ui.seller.list_product

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.model.Product
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
): ViewModel() {
    val listProduct = MutableLiveData<List<Product>>()
    val quantity = MutableLiveData<String>("")


    fun getListProductBySeller(){
        viewModelScope.launch {
            val idUser = Prefs.newInstance(context).getId()
            responsitoryImpl.getListProductBySellerId(idUser).apply {
                if (this.errors.isEmpty()){
                    listProduct.value = this.dataResponse ?: listOf()
                    quantity.value = "${this.dataResponse.size} Products"
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }


}