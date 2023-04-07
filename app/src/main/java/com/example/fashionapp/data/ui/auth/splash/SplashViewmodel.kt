package com.example.fashionapp.data.ui.auth.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SplashViewmodel @Inject constructor(
    responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext context: Context
):ViewModel() {
}