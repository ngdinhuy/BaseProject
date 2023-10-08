package com.example.fashionapp.data.ui.auth.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {
    val password = MutableLiveData<String>("")
    val usernmae = MutableLiveData<String>("")
    val enableButton = MutableLiveData<Boolean>(false)
    val stateLogin = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    fun validate() {
        enableButton.value = !(password.value.isNullOrBlank() || password.value!!.length < 6 ||
                usernmae.value.isNullOrBlank())
    }

    fun loginClick() {
        if (!(password.value.isNullOrBlank() || password.value.isNullOrEmpty() ||
                    usernmae.value.isNullOrBlank() || usernmae.value.isNullOrEmpty())) {
            isLoading.value = true
            viewModelScope.launch {
                val loginResponse = responsitoryImpl.login(
                    password = password.value!!,
                    username = usernmae.value!!
                )
                if (loginResponse.errors.isEmpty()) {
                    isLoading.value = false
                    stateLogin.value = true
                    Prefs.newInstance(context = context).setUsername(username = usernmae.value!!)
                    Prefs.newInstance(context).setId(loginResponse.dataResponse.id?: 0)
                } else{
                    isLoading.value = false
                    context.makeToast(loginResponse.errors[0])
                }
            }
        }
    }

}