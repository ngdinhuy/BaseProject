package com.example.baseproject.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baseproject.utils.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


abstract class BaseViewmodel: ViewModel() {
    val isLoading = MutableStateFlow<Boolean> (false)
    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
    }
}