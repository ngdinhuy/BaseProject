package com.example.baseproject.helper

class DecryptHelper {

    companion object {
        init {
            System.loadLibrary("decrypt-lib")
        }
    }

    external fun getStringFromJNI(): String

    external fun encryptNative(plainText: String): String

    external fun decryptNative(cipherText: String): String
}