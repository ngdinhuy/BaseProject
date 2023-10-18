package com.example.fashionapp.ui.seller.add_product

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
): ViewModel() {
    val listCategory = ArrayList<String>()
    val name = MutableLiveData<String>("")
    val description = MutableLiveData<String>("")
    val price = MutableLiveData<String>("")
    val discount = MutableLiveData<String>("")
    val quantity = MutableLiveData<String>("")

    var type = MutableLiveData<String>("Choose")
    var typeInt = 0

    var listImage = MutableLiveData<String>()
    fun getListCategory(){
        viewModelScope.launch {
            responsitoryImpl.getAllCategories().apply {
                if (errors.isEmpty()){
                    dataResponse.forEach {
                        listCategory.add(it.title)
                    }
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

    fun setUpType(idType:Int){
        typeInt = idType
        type.value = listCategory[idType]
    }
}