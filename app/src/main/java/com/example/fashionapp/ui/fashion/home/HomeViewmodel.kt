package com.example.fashionapp.ui.fashion.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.Define
import com.example.fashionapp.R
import com.example.fashionapp.model.Product
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.utils.Event
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {
    private val listProductInMenClothes = MutableLiveData<List<Product>>()
    private val listProductInWomenClothes = MutableLiveData<List<Product>>()
    val mapInfo = MutableLiveData<Map<String, Any?>>()
    lateinit var fashionViewmodel: FashionViewmodel
    fun getProductByCategory() {
        viewModelScope.launch {
            fashionViewmodel._isLoading.value = Event(true)
            listProductInMenClothes.value =
                responsitoryImpl.getProductsByCategory(Define.MEN_CLOTHES)
            listProductInWomenClothes.value =
                responsitoryImpl.getProductsByCategory(Define.WOMEN_CLOTHES)
            fashionViewmodel._isLoading.value = Event(false)
            mapInfo.value = mapOf<String, Any?>(
                Define.MEN_CLOTHES to listProductInMenClothes.value,
                Define.WOMEN_CLOTHES to listProductInWomenClothes.value
            )
        }
    }

    fun goToFashionSale(){
        Toast.makeText(context,context.resources.getString(R.string.feature_is_developing),Toast.LENGTH_SHORT).show()
    }
}