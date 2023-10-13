package com.example.fashionapp.ui.fashion.profile.setting

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.component.ChangePasswordBottomSheetDialog
import com.example.fashionapp.component.ClickEvent
import com.example.fashionapp.data.remote.request.UpdateUserInfoRequest
import com.example.fashionapp.data.remote.response.UserInfoResponse
import com.example.fashionapp.model.UserModel
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel(), ClickEvent{
    val fullName = MutableLiveData<String>()
    val dob = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val clickChangePasswordEvent = MutableLiveData<Event<Unit>>()

    fun getInfo() {
        val idUser = Prefs.newInstance(context).getId();
        viewModelScope.launch {
            responsitoryImpl.getUserInfo(idUser).apply {
                if (this.errors.isEmpty()) {
                    val data = this.dataResponse ?: UserInfoResponse()
                    fullName.value = data.name ?: ""
                    dob.value = data.dob?: ""
                    email.value = data.email ?: ""
                    phoneNumber.value = data.phoneNumber ?: ""
                    password.value = data.password ?: ""
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

    fun updateUserInfo() {
        val idUser = Prefs.newInstance(context).getId();
        viewModelScope.launch {
            responsitoryImpl.updateInfoUser(
                idUser,
                UpdateUserInfoRequest(fullName.value, phoneNumber.value, email.value, dob.value)
            ).apply {
                if (this.errors.isEmpty()) {
                    val data = this.dataResponse ?: UserInfoResponse()
                    fullName.value = data.name ?: ""
                    dob.value = data.dob?: ""
                    email.value = data.email ?: ""
                    phoneNumber.value = data.phoneNumber ?: ""
                    password.value = data.password ?: ""
                    context.makeToast("Update Success")
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

    fun clickChangePassword(){
        clickChangePasswordEvent.value = Event(Unit)
    }
    private fun updatePassword(oldPassword: String, newPassword: String){
        val idUser = Prefs.newInstance(context).getId()
        viewModelScope.launch {
            viewModelScope.launch {
                responsitoryImpl.updatePassword(idUser, newPassword, oldPassword).apply {
                    if (this.errors.isEmpty()) {
                        val data = this.dataResponse ?: UserInfoResponse()
                        password.value = data.password ?: ""
                        context.makeToast("Update Success")
                    } else {
                        context.makeToast(errors[0])
                    }
                }
            }
        }
    }

    override fun clickSave(oldPassword: String, newPassword: String) {
        updatePassword(oldPassword, newPassword)
    }
}