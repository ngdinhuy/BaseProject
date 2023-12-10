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

    fun loadListProduct() {
        viewModelScope.launch {
            impl.getAllProducts().apply {
                if (errors.isEmpty()) {
                    fullList = dataResponse
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }
}