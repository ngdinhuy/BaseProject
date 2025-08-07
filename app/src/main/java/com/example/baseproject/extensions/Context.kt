package com.example.baseproject.extensions

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat

fun Context.hasPermissionsAccessImage(): Boolean {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    return ContextCompat.checkSelfPermission(this, permission) == android.content.pm.PackageManager.PERMISSION_GRANTED
}