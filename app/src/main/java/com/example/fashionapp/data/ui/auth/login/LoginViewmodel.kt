package com.example.fashionapp.data.ui.auth.login

import android.content.Context
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
    @ApplicationContext context: Context
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
                if (loginResponse.token.isNotEmpty()) {
                    isLoading.value = false
                    stateLogin.value = true
                    Prefs.setToken(loginResponse.token)
                } else{
                    isLoading.value = false
                }
            }
        }
    }

}