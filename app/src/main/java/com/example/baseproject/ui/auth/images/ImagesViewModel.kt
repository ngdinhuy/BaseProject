package com.example.baseproject.ui.auth.images

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.baseproject.base.BaseViewmodel
import com.example.baseproject.helpers.ImagesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(val imagesHelper: ImagesHelper): BaseViewmodel() {
    val flow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        ImagesPagingSource(imagesHelper)
    }.flow.cachedIn(viewModelScope)
}