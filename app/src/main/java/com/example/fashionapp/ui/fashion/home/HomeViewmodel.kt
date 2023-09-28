package com.example.fashionapp.ui.fashion.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.Define
import com.example.fashionapp.R
import com.example.fashionapp.data.remote.response.CategoryAndProductResponse
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
class HomeViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {
    val infoCateProduct = MutableLiveData<List<CategoryAndProductResponse>?>()
    lateinit var fashionViewmodel: FashionViewmodel
    fun getProductByCategory() {
        viewModelScope.launch {
//            fashionViewmodel._isLoading.value = Event(true)
//            val response1 = responsitoryImpl.getProductsByCategory(2)
//            val response2 = responsitoryImpl.getProductsByCategory(2)
//            listProductInMenClothes.value = response1.dataResponse as? List<Product>
//            listProductInWomenClothes.value = response2.dataResponse as? List<Product>
//            fashionViewmodel._isLoading.value = Event(false)
//            mapInfo.value = mapOf<String, Any?>(
//                Define.MEN_CLOTHES to listProductInMenClothes.value,
//                Define.WOMEN_CLOTHES to listProductInWomenClothes.value
//            )
            fashionViewmodel._isLoading.value = Event(true)
            responsitoryImpl.getCategoryAndProduct().apply {
                if (this.errors.isEmpty()){
                    infoCateProduct.value = this.dataResponse
                } else {
                    context.makeToast(errors[0])
                }
            }
            fashionViewmodel._isLoading.value = Event(false)
        }
    }

    fun goToFashionSale(){
        Toast.makeText(context,context.resources.getString(R.string.feature_is_developing),Toast.LENGTH_SHORT).show()
    }
}