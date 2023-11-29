package com.example.fashionapp.ui.seller.add_product

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.remote.request.RequestProduct
import com.example.fashionapp.model.CategoryModel
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddProductViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {
    var idProduct = 0
    var isAddProduct = true
    val listCategory = ArrayList<String>()
    val name = MutableLiveData<String>("")
    val description = MutableLiveData<String>("")
    val price = MutableLiveData<String>("")
    val discount = MutableLiveData<String>("")
    val quantity = MutableLiveData<String>("")

    var type = MutableLiveData<String>("Choose")
    var typeInt = 0

    var listImage = ArrayList<String>()
    var mutableListImage = MutableLiveData<ArrayList<String>>()

    var backEvent =  MutableLiveData<Event<Unit>>()
    fun getInfoProduct() {
        if (idProduct != 0) {
            viewModelScope.launch {
                responsitoryImpl.getProductById(idProduct).apply {
                    if (errors.isEmpty()) {
                        name.value = dataResponse.name ?: ""
                        description.value = dataResponse.description ?: ""
                        price.value = (dataResponse.price ?: "").toString()
                        discount.value = dataResponse.discount.toString()
                        quantity.value = dataResponse.quantity.toString()
                        mutableListImage.value = ArrayList((dataResponse.image ?: listOf()))
                        typeInt = dataResponse.idCategory ?: 0
                        type.value = dataResponse.categoryName ?: ""
                    } else {
                        context.makeToast(errors[0])
                    }
                }
            }
        }
    }

    fun getListCategory() {
        viewModelScope.launch {
            responsitoryImpl.getAllCategories().apply {
                if (errors.isEmpty()) {
                    dataResponse.forEach {
                        listCategory.add(it.title)
                        getInfoProduct()
                    }
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

    fun setUpType(idType: Int) {
        typeInt = idType
        type.value = listCategory[idType]
    }

    fun saveProduct() {
        val idUser = Prefs.newInstance(context).getId()


        val listMultipart = ArrayList<MultipartBody.Part>()
        listImage.forEach {
            val file = File(it)
            val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val imagePart = MultipartBody.Part.createFormData("multipartFiles", file.name, requestBody)
            listMultipart.add(imagePart)
        }
        typeInt++
        viewModelScope.launch {
            if (isAddProduct) {
                val requestProduct = RequestProduct(
                    idSeller = idUser.toString(),
                    name = name.value ?: "",
                    description = description.value ?: "",
                    quantity = quantity.value ?: "",
                    price = price.value ?: "",
                    discount = discount.value ?: "",
                    idCategory = typeInt.toString()
                )
                responsitoryImpl.insertProduct(requestProduct, listMultipart).apply {
                    if (errors.isEmpty()) {
                        context.makeToast("Success")
                        backEvent.value = Event(Unit)
                    } else {
                        context.makeToast(errors[0])
                    }
                }
            } else {
                val updateRequest = RequestProduct(
                    name = name.value ?: "",
                    description = description.value ?: "",
                    quantity = quantity.value ?: "",
                    price = price.value ?: "",
                    discount = discount.value ?: "",
                    idCategory = typeInt.toString(),
                    id = idProduct
                )
                responsitoryImpl.updateProduct(request = updateRequest, listMultipart).apply {
                    if (errors.isEmpty()){
                        context.makeToast("Success")
                        backEvent.value = Event(Unit)
                    } else {
                        context.makeToast(errors[0])
                    }
                }
            }

        }

    }
}