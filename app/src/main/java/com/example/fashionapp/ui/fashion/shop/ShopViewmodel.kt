package com.example.fashionapp.ui.fashion.shop

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.utils.Event
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext context: Context
): ViewModel() {
    private val _listCategories = MutableLiveData<Event<List<String>>>()
    val listCategories : LiveData<Event<List<String>>> = _listCategories

    var fashionViewModel : FashionViewmodel? = null
    fun getAllCategories(){
        fashionViewModel?.let {
            it._isLoading.value = Event(true)
            viewModelScope.launch {
                _listCategories.value = Event(responsitoryImpl.getAllCategories())
                it._isLoading.value = Event(false)
            }
        }
    }


}