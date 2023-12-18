package com.example.fashionapp.data.ui.auth.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.remote.request.SignUpRequest
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.Utils
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {
    val name = MutableLiveData<String>("")
    val username = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val role = MutableLiveData(0)
    val enableButton = MutableLiveData<Boolean>(false)

    val loginSuccessEvent = MutableLiveData<Event<Int>>()

    fun validate() {
        if (name.value.isNullOrBlank()
            || username.value.isNullOrBlank()
            || password.value.isNullOrBlank()
            || role.value == 0) {
            enableButton.value = false
            return
        }
        if(!Utils.validatePassword(password = password.value!!).isNullOrBlank()){
            enableButton.value = false
            return
        }
        enableButton.value = true
    }

    fun clickRole(roleInt:Int){
        role.value = roleInt
        validate()
    }

    fun signUpClick(){
        viewModelScope.launch {
            responsitoryImpl.signUp(
                SignUpRequest(name = name.value ?:"",
                    username = username.value?: "",
                    password = password.value?: "",
                    role = role.value.toString())).apply {
                if (this.errors.isEmpty()){
                    val response = this.dataResponse
                    context.makeToast("Register success")
                    loginSuccessEvent.value = Event(response.role?: -1)
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }
}