package com.example.fashionapp.data.ui.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.utils.Prefs
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
        enableButton.value = !(password.value.isNullOrBlank() || password.value.isNullOrEmpty() ||
                usernmae.value.isNullOrBlank() || usernmae.value.isNullOrEmpty())
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
                    val data = loginResponse.dataResponse
                    Prefs.newInstance(context = context).setToken(data.username)
                    Prefs.newInstance(context = context).setUsername(username = usernmae.value!!)
                    Log.e("token",Prefs.newInstance(context).getToken().toString())
                } else{
                    isLoading.value = false
                }
            }
        }
    }

}