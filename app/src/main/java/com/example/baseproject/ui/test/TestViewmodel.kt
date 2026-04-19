package com.example.baseproject.ui.test

import android.content.Context
import com.example.baseproject.base.BaseViewmodel
import com.example.baseproject.data.ShopAppResponsitoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class TestViewmodel @Inject constructor(
    val responsitoryImpl: ShopAppResponsitoryImpl,
    @ApplicationContext val context: Context
) : BaseViewmodel() {

}
