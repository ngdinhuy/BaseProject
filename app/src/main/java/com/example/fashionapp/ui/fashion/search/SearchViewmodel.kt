package com.example.fashionapp.ui.fashion.search

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.model.Product
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    val impl: ShopAppResponsitoryImpl
) : ViewModel() {
    var fullList = listOf<Product>()
    val listProduct = MutableLiveData<List<Product>>()
    var idSeller = 0
    fun loadListProduct() {
        viewModelScope.launch {
            if (idSeller == 0){
                impl.getAllProducts().apply {
                    if (errors.isEmpty()) {
                        fullList = dataResponse
                    } else {
                        context.makeToast(errors[0])
                    }
                }
            } else {
                impl.getListProductBySellerId(idSeller).apply {
                    if (errors.isEmpty()) {
                        fullList = dataResponse
                    } else {
                        context.makeToast(errors[0])
                    }
                }
            }

        }
    }
}