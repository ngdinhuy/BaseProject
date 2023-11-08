package com.example.fashionapp.ui.fashion.chat

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.remote.response.ChatDetailResponse
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : ViewModel() {
    var namePartner = ""
    var idPartner = 0
    var offset = 0
    val listMessageDetail = MutableLiveData<ArrayList<ChatDetailResponse>>()
    var listMessage = ArrayList<ChatDetailResponse>()
    fun loadMessage(){
        val idUser = Prefs.newInstance(context).getId()
        viewModelScope.launch {
            responsitoryImpl.getChatListDetail(idUser, idPartner, offset).apply {
                if (errors.isEmpty()){
                    val response = dataResponse
                    response.addAll(response.size, listMessage)
                    listMessage = dataResponse
                    listMessageDetail.value = listMessage
                    this@ChatViewmodel.offset = offset
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }

}