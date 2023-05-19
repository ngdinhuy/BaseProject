package com.example.fashionapp.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.FrameLayout
import com.example.fashionapp.R

class CustomDialog(context: Context) : Dialog(context) {

    var flZalo : FrameLayout? = null
    var flMomo : FrameLayout? = null
    var clickPayEvent: ClickPayEvent? = null

    fun passClickPayEvent(clickPayEvent: ClickPayEvent){
        this.clickPayEvent = clickPayEvent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flZalo = findViewById(R.id.fl_zalopay)
        flMomo = findViewById(R.id.fl_momo)

        flZalo?.setOnClickListener {
            clickPayEvent?.clickZalo()
            this.dismiss()
        }

        flMomo?.setOnClickListener{
            clickPayEvent?.clickMomo()
            this.dismiss()
        }
    }
    companion object{
        fun newInstancePaymentDialog(context: Context):CustomDialog{
            val dialog = CustomDialog(context).apply {
                setContentView(R.layout.dialog_payment)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            return dialog
        }
    }
    interface ClickPayEvent{
        fun clickZalo()

        fun clickMomo()
    }
}