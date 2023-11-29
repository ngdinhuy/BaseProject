package com.example.fashionapp.ui.fashion.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.Role
import com.example.fashionapp.component.ClickEvent
import com.example.fashionapp.component.WithdrawEvent
import com.example.fashionapp.data.local.MyResponsitory
import com.example.fashionapp.data.remote.response.UserInfoResponse
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.ui.seller.SellerViewmodel
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
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
) : ViewModel(), ClickEvent, WithdrawEvent {
    lateinit var fashionViewmodel: FashionViewmodel
    var sellerViewmodel: SellerViewmodel? = null
    val userInfoResponse = MutableLiveData<UserInfoResponse>()
    val clickChangePasswordEvent = MutableLiveData<Event<Unit>>()


    fun logout() {
        if (Prefs.newInstance(context).getRole() == Role.CUSTOMER) {
            fashionViewmodel.logout()
        } else {
            sellerViewmodel?.let {
                it.logout()
            }
        }
    }

    fun goToMyOrder() {
        if (Prefs.newInstance(context).getRole() == Role.CUSTOMER) {
            fashionViewmodel.goToMyOrder()
        } else {
            sellerViewmodel?.let {
                it.goToListOrderSeller()
            }
        }
    }

    fun goToSetting() {
        if (Prefs.newInstance(context).getRole() == Role.CUSTOMER) {
            fashionViewmodel.goToSetting()
        } else {
            sellerViewmodel?.goToSetting()
        }
    }

    fun getInfoUser() {
        val idUser = Prefs.newInstance(context).getId()
        viewModelScope.launch {
            responsitoryImpl.getUserInfo(idUser).apply {
                if (this.errors.isEmpty()) {
                    userInfoResponse.value = this.dataResponse ?: UserInfoResponse()
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

    fun clickChangePassword() {
        clickChangePasswordEvent.value = Event(Unit)
    }

    override fun clickSave(oldPassword: String, newPassword: String) {
        updatePassword(oldPassword, newPassword)
    }

    private fun updatePassword(oldPassword: String, newPassword: String) {
        val idUser = Prefs.newInstance(context).getId()
        viewModelScope.launch {
            viewModelScope.launch {
                responsitoryImpl.updatePassword(idUser, newPassword, oldPassword).apply {
                    if (this.errors.isEmpty()) {
                        val data = this.dataResponse ?: UserInfoResponse()
                        context.makeToast("Update success: new password: ${data.password}")
                    } else {
                        context.makeToast(errors[0])
                    }
                }
            }
        }
    }

    override fun withdraw(
        money: String,
        mailPaypal: String,
        password: String,
        isSaveAccount: Boolean
    ) {
        viewModelScope.launch {
            val idUser = Prefs.newInstance(context).getId()
            responsitoryImpl.sellerWithdraw(idUser, money.toDouble(), mailPaypal, password, isSaveAccount).apply {
                if (errors.isEmpty()) {
                    userInfoResponse.value = dataResponse ?: UserInfoResponse()
                    context.makeToast("Withdraw is success")
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

}