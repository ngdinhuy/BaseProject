package com.example.baseproject.helpers

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import com.example.baseproject.data.local.ImageModel
import dagger.hilt.android.qualifiers.ApplicationContext

class ImagesHelper(@ApplicationContext val context: Context) {

    fun getImagesFromGallery(offset: Int, limit: Int): List<Uri> {
        val imageUris = mutableListOf<Uri>()
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID
        )

        val query = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val queryArgs = Bundle().apply {
                putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, arrayOf(MediaStore.Images.Media.DATE_ADDED))
                putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, ContentResolver.QUERY_SORT_DIRECTION_DESCENDING)
                putInt(ContentResolver.QUERY_ARG_LIMIT, limit)
                putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
            }
            context.contentResolver.query(
                collection,
                projection,
                queryArgs,
                null,
            )
        } else {
            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC LIMIT $limit OFFSET $offset"
            context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                sortOrder
            )
        }

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(collection, id)
                imageUris.add(contentUri)
            }
        }

        return imageUris
    }

    fun getImageDateTimeById(id: Long): ImageModel {
        val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

        val projection = arrayOf(
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DATE_ADDED
        )

        context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val dateTakenIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
                val dateAddedIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

                val dateTaken = cursor.getLong(dateTakenIndex)
                val dateAdded = cursor.getLong(dateAddedIndex)

                return ImageModel(id, dateTaken, dateAdded * 1000, uri)
            }
        }

        return ImageModel(id, uri = uri)
    }
}