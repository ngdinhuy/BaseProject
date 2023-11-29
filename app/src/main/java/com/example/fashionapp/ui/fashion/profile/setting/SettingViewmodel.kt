package com.example.fashionapp.ui.fashion.profile.setting

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.Define
import com.example.fashionapp.data.remote.request.UpdateUserInfoRequest
import com.example.fashionapp.data.remote.response.UserInfoResponse
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
class SettingViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {
    val fullName = MutableLiveData<String>()
    val dob = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val avatar = MutableLiveData<String>("")
    var isChangeAvatar = false

    private val _backEvent = MutableLiveData<Event<Unit>>()
    val backEvent: LiveData<Event<Unit>> = _backEvent

    fun getInfo() {
        val idUser = Prefs.newInstance(context).getId();
        viewModelScope.launch {
            responsitoryImpl.getUserInfo(idUser).apply {
                if (this.errors.isEmpty()) {
                    val data = this.dataResponse ?: UserInfoResponse()
                    fullName.value = data.name ?: ""
                    dob.value = data.dob ?: ""
                    email.value = data.email ?: ""
                    phoneNumber.value = data.phoneNumber ?: ""
                    password.value = data.password ?: ""
                    avatar.value = data.avatar ?: ""
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
                if (errors.isEmpty()) {
                    avatar.value?.let {
                        if (it.isNotBlank() && !it.contains(Define.HTTP)) {
                            updateAvatar()
                        } else {
                            context.makeToast("Success")
                            _backEvent.value = Event(Unit)
                        }
                    }
                } else {
                    context.makeToast(errors[0])
                }
            }

        }
    }

    fun updateAvatar() {
        val idUser = Prefs.newInstance(context).getId();
        viewModelScope.launch {
            try {
                val file = File(avatar.value)
                val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                val imagePart =
                    MultipartBody.Part.createFormData("multipartFile", file.name, requestBody)
                responsitoryImpl.changeAvatar(idUser, imagePart).apply {
                    if (errors.isEmpty()) {
                        context.makeToast("Success")
                        _backEvent.value = Event(Unit)
                    } else {
                        context.makeToast(errors[0])
                    }
                }
            } catch (e: Exception) {
                Log.e("Error Up load avatar ", e.message.toString())
            }
        }
    }
}