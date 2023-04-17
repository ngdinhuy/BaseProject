package com.example.fashionapp.ui.fashion.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.EntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

@EntryPoint
class CartViewmodel(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext context: Context
): ViewModel() {
}