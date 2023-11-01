package com.example.fashionapp.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import com.example.fashionapp.R
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.OrderRequest
import com.paypal.checkout.order.PurchaseUnit
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

        paymentButtonContainer.setup(
            createOrder =
            CreateOrder { createOrderActions ->
                val order =
                    OrderRequest(
                        intent = OrderIntent.CAPTURE,
                        appContext = AppContext(userAction = UserAction.PAY_NOW),
                        purchaseUnitList =
                        listOf(
                            PurchaseUnit(
                                amount =
                                Amount(currencyCode = CurrencyCode.USD, value = "10.00")
                            )
                        )
                    )
                createOrderActions.create(order)
            },
            onApprove =
            OnApprove { approval ->
                approval.orderActions.capture { captureOrderResult ->
                    Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
                }
            }
        )
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