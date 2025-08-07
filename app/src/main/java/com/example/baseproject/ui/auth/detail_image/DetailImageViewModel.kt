package com.example.baseproject.ui.auth.detail_image

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.baseproject.base.BaseViewmodel
import com.example.baseproject.data.local.ImageModel
import com.example.baseproject.helpers.ImagesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailImageViewModel @Inject constructor(
    val imagesHelper: ImagesHelper,
): BaseViewmodel() {
    private val _detailImage = MutableStateFlow<ImageModel?>(null)
    val detailImage: Flow<ImageModel?> = _detailImage
    private val _backEvent = MutableSharedFlow<Unit>()
    val backEvent: SharedFlow<Unit> = _backEvent

    fun queryDetailImage(id: Long) {
        _detailImage.value = imagesHelper.getImageDateTimeById(id)
    }

    fun clickBack() {
        viewModelScope.launch {
            _backEvent.emit(Unit)
        }
    }
}