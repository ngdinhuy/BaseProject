package com.example.fashionapp.ui.fashion

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class FashionViewmodel(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext context: Context
): ViewModel() {

}