package com.example.fashionapp.utils

import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.fashionapp.Define
import java.io.ByteArrayOutputStream
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar


class Utils {
    companion object {
        fun validatePassword(password: String): String? {
            if (password.length < 8 || password.length > 20) {
                return "Password must be less than 20 and more than 8 characters"
            }
            val upperCaseChars = "(.*[A-Z].*)"
            if (!password.matches(upperCaseChars.toRegex())) {
                return "Password must have atleast one uppercase character"
            }
            val lowerCaseChars = "(.*[a-z].*)"
            if (!password.matches(lowerCaseChars.toRegex())) {
                return "Password must have atleast one lowercase character"
            }
            val numbers = "(.*[0-9].*)"
            if (!password.matches(numbers.toRegex())) {
                return "Password must have atleast one number"
            }
            val specialChars = "(.*[@,#,$,%].*$)"
            return if (!password.matches(specialChars.toRegex())) {
                "Password must have atleast one special character"
            } else null
        }

        fun checkListPermission(context: Context, permissions: List<String>): Boolean {
            permissions.forEach {
                if (ContextCompat.checkSelfPermission(
                        context,
                        it
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
            return true
        }

        fun getImageUri(context: Context, image: Bitmap): String {
            val bytes = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, bytes)
            val uri = MediaStore.Images.Media.insertImage(
                context!!.contentResolver,
                image,
                "IMG_" + Calendar.getInstance().time,
                null
            )
            return uri
        }

        fun getCurrentMonth(): String? {
            return (Calendar.getInstance()[Calendar.MONTH] + 1).toString() + "/" + Calendar.getInstance()[Calendar.YEAR]
        }

        fun getMapStatistic(map: Map<String, Double>): MutableMap<String, Double> {
            var result = mutableMapOf<String, Double>()
            Define.listMonthOfYear.forEach {
                result[it] = map[it] ?: 0.0
            }
            return result
        }

        fun getPathFromUri(context: Context, uri: Uri): String? {
            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(
                        split[1]
                    )
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            } else if ("content".equals(uri.getScheme(), ignoreCase = true)) {

                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.getLastPathSegment() else getDataColumn(
                    context,
                    uri,
                    null,
                    null
                )
            } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
                return uri.getPath()
            }
            return null
        }

        fun getDataColumn(
            context: Context, uri: Uri?, selection: String?,
            selectionArgs: Array<String>?
        ): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(
                column
            )
            try {
                cursor = uri?.let {
                    context.contentResolver.query(
                        it, projection, selection, selectionArgs,
                        null
                    )
                }
                if (cursor != null && cursor.moveToFirst()) {
                    val index: Int = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                if (cursor != null) cursor.close()
            }
            return null
        }


        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.getAuthority()
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.getAuthority()
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.getAuthority()
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.getAuthority()
        }
    }
}

fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Double.get2digit(): Double {
    return try {
        var df = DecimalFormat("#.##")
        if (this.toString().contains(",")){
            df = DecimalFormat("#,##")
        }
        df.roundingMode = RoundingMode.CEILING
        df.format(this).toDouble()
    } catch (e: Exception){
        Log.e("Utils", e.message.toString())
        this
    }

}



