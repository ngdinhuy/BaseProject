package com.example.baseproject.ui.auth.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.baseproject.base.BaseViewmodel
import com.example.baseproject.data.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SplashViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
): BaseViewmodel() {

}