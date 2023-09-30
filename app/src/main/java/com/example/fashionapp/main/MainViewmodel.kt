package com.example.fashionapp.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shopapp.data.remote.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    val responsitoryImpl: ShopAppResponsitoryImpl
):ViewModel() {

}