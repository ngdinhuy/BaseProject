package com.example.fashionapp.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.FrameLayout
import com.example.fashionapp.R
import com.paypal.checkout.paymentbutton.PaymentButtonContainer

class CustomDialog(context: Context) : Dialog(context) {

    lateinit var flZalo : FrameLayout
    lateinit var flMomo : FrameLayout
    lateinit var paymentButtonContainer: PaymentButtonContainer
    var clickPayEvent: ClickPayEvent? = null

    fun passClickPayEvent(clickPayEvent: ClickPayEvent){
        this.clickPayEvent = clickPayEvent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_payment)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        flZalo = findViewById(R.id.fl_positive_button)
        flMomo = findViewById(R.id.fl_negative_button)
        paymentButtonContainer = findViewById(R.id.payment_button_container)

//        flZalo.setOnClickListener {
//            clickPayEvent?.clickZalo()
//            this.dismiss()
//        }
//
//        flMomo.setOnClickListener{
//            clickPayEvent?.clickMomo()
//            this.dismiss()
//        }
    }

    fun setFlZaloClick(zaloClick: (CustomDialog) -> Unit): CustomDialog {
        flZalo.setOnClickListener{
            zaloClick(this)
        }
        return this
    }

    fun setShipCodeClick(shipCodeClick: (CustomDialog) -> Unit): CustomDialog {
        flMomo.setOnClickListener{
            shipCodeClick(this)
        }
        return this
    }

    fun showDialog() : CustomDialog {
        if (!isShowing){
            this.show()
        }
        return this
    }
    companion object{
        fun newInstancePaymentDialog(context: Context): CustomDialog {
            val dialog = CustomDialog(context)
            return dialog
        }
    }
    interface ClickPayEvent{
        fun clickZalo()

        fun clickMomo()
    }
}