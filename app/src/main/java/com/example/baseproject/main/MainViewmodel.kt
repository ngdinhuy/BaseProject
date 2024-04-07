package com.example.baseproject.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.baseproject.data.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    @ApplicationContext val context: Context,
    val responsitoryImpl: ShopAppResponsitoryImpl
):ViewModel() {
}