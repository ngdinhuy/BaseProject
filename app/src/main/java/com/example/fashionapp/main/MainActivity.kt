package com.example.fashionapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.fashionapp.R
import dagger.hilt.android.AndroidEntryPoint
import vn.zalopay.sdk.ZaloPaySDK

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val mainViewmodel: MainViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    protected override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }
}