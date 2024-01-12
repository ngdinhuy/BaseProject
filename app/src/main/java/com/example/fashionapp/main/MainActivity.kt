package com.example.fashionapp.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.fashionapp.Define
import com.example.fashionapp.R
import com.example.fashionapp.utils.Prefs
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import vn.zalopay.sdk.ZaloPaySDK

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val mainViewmodel: MainViewmodel by viewModels()
    private var mSocket: Socket? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        setUpUserOnline()
    }

    private fun setUpUserOnline() {
        try {
            val myID = Prefs.newInstance(this).getId()
            if (myID ==0 ) return
            val options = IO.Options().apply {
                forceNew = true
                reconnectionAttempts = Integer.MAX_VALUE
                reconnection = true
                query = "id_user=$myID"
            }
            mSocket = IO.socket(Define.SOCKET_URL, options)
            mSocket?.on(Socket.EVENT_CONNECT){
                Log.e("Socket: ","Connect succees: id_user=$myID")
            }

            mSocket?.on(Socket.EVENT_DISCONNECT){
                Log.e("Socket: ", "Disconnect")
            }

            mSocket?.on(Socket.EVENT_CONNECT_ERROR){
                Log.e("Socket: ", "Error ${it[0].toString()}")
            }

//            mSocket?.on(Define.DISCONNECT, Emitter.Listener {
//                val json = it[0] as? JsonObject
//                Log.e("Socket event:", json.toString())
//            })

            mSocket?.connect()
        } catch (e: Exception){
            Log.e("Socket error", e.message.toString())
        }
    }

    protected override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
//        ZaloPaySDK.getInstance().onResult(intent)
    }

    override fun onPause() {
        super.onPause()
        logOut()
    }

    fun logOut(){
        val myID = Prefs.newInstance(this).getId()
        if (myID ==0 ) return
        mSocket?.emit(Define.DISCONNECT, myID.toString())
        mSocket?.disconnect()
        mSocket?.off(Define.DISCONNECT)
    }
}