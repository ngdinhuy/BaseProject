package com.example.baseproject.ui.auth.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.baseproject.data.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SplashViewmodel @Inject constructor(
    responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext context: Context
):ViewModel() {
}