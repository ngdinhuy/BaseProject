package com.example.baseproject.helper

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object CheckPermissionHelper {
    fun hasPermissions(context: Context?, permissions: List<String>): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }
}