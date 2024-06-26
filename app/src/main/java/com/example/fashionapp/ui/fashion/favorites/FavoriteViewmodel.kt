package com.example.fashionapp.ui.fashion.favorites

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionapp.data.remote.response.ChatListResponse
import com.example.fashionapp.utils.Event
import com.example.fashionapp.utils.Prefs
import com.example.fashionapp.utils.makeToast
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
): ViewModel() {
    val listChat = MutableLiveData<Event<List<ChatListResponse>>>()
    val chatResponse = MutableLiveData<Event<ChatListResponse>>()
    fun getListChat(){
        val idUser = Prefs.newInstance(context).getId()
        viewModelScope.launch {
            responsitoryImpl.getChatList(idUser).apply {
                if (errors.isEmpty()){
                    listChat.value = Event(dataResponse)
                } else {
                    context.makeToast(errors[0])
                }
            }
        }
    }
}