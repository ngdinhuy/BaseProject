package com.example.baseproject.ui.auth.images

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.baseproject.helpers.ImagesHelper
import kotlinx.coroutines.runBlocking

class ImagesPagingSource(val imagesHelper: ImagesHelper): PagingSource<Int, Uri>() {

    companion object {
        const val QUERY_LIMIT = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Uri>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Uri> {
        val offset = params.key ?: 0
        var data = runBlocking {
             imagesHelper.getImagesFromGallery(offset, QUERY_LIMIT)
        }
        var size = data.size
        if (size < QUERY_LIMIT) {
            return LoadResult.Page(
                data = data,
                prevKey = if (offset == 0) null else offset - QUERY_LIMIT,
                nextKey = null
            )
        } else {
            return LoadResult.Page(
                data = data,
                prevKey = if (offset == 0) null else offset - QUERY_LIMIT,
                nextKey = offset + QUERY_LIMIT
            )
        }
    }
}