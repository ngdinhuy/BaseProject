package com.example.baseproject.ui.test

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.baseproject.base.BaseViewmodel
import com.example.baseproject.data.ShopAppResponsitoryImpl
import com.example.baseproject.helper.DecryptHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.yield
import javax.inject.Inject

@HiltViewModel
class TestViewmodel @Inject constructor(
    private val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext private val context: Context,
    private val decryptHelper: DecryptHelper,
) : BaseViewmodel() {
    companion object {
        private const val TAG = "TestViewmodel"
    }
    fun initDecryptHelper() {
        Log.d(TAG, "initDecryptHelper: ${decryptHelper.getStringFromJNI()}")
        Log.d(TAG, "initDecryptHelper: ${decryptHelper.encryptNative("Nguyen Dinh Huy")}")
    }
}
