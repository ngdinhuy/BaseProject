package com.example.baseproject.data.local

import android.net.Uri

data class ImageModel(val id: Long, val dateTaken: Long? = null, val dateAdded: Long? = null, val uri: Uri? = null) {
}