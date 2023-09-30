package com.example.fashionapp.ui.fashion.shop

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.model.Product
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _listCategories = MutableLiveData<Event<List<CategoryModel>>>()
    val listCategories: LiveData<Event<List<CategoryModel>>> = _listCategories

    private val _listProduct = MutableLiveData<List<Product>>()
    val listProduct : LiveData<List<Product>> = _listProduct

    var fashionViewModel: FashionViewmodel? = null
    fun getAllCategories() {
        viewModelScope.launch {
            responsitoryImpl.getAllCategories().apply {
                if (this.errors.isEmpty()) {
                    _listCategories.value = Event(this.dataResponse)
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

    fun getListProduct(idCategory: Int){
        viewModelScope.launch {
            responsitoryImpl.getProductsByCategory(idCategory).apply {
                if (this.errors.isEmpty()) {
                    _listProduct.value = this.dataResponse?: listOf()
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

}