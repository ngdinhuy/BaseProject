package com.example.fashionapp.ui.fashion.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.local.MyResponsitory
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.Prefs
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context,
    val myResponsitory: MyResponsitory
): ViewModel() {
    lateinit var fashionViewmodel : FashionViewmodel
    val countBill = MutableLiveData<Int>(0)

    fun logout(){
        fashionViewmodel.logout()
//        Toast.makeText(context,"OK",Toast.LENGTH_SHORT).show()
    }

    fun goToMyOrder(){
        fashionViewmodel.goToMyOrder()
    }

    fun getCountBillFromLocal(){
        viewModelScope.launch(Dispatchers.IO) {
            countBill.postValue(myResponsitory.getAnmountBill(Prefs.newInstance(context).getUsername()!!))
        }
    }
}