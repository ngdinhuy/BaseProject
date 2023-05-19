package com.example.fashionapp.ui.fashion.cart

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionapp.adapter.CartAdapter
import com.example.fashionapp.databinding.FragmentCartBinding
import com.example.fashionapp.ui.dialog.CustomDialog
import com.example.fashionapp.utils.EventObserver
import com.example.fashionapp.zalo_payment.Api.CreateOrder
import dagger.hilt.android.AndroidEntryPoint
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class CartFragment: Fragment() {
    lateinit var databinding : FragmentCartBinding
    val cartViewmodel: CartViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentCartBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = cartViewmodel
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        cartViewmodel.getAllCart()
        setUpEvent()
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX)
    }

    fun setUpAdapter(){
        cartViewmodel.listCard.observe(viewLifecycleOwner, EventObserver{
            databinding.rvCart.apply {
                adapter = CartAdapter(it, requireContext()).apply {
                    passClickEvent(cartViewmodel)
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }

    private fun setUpEvent() {
        cartViewmodel.clickCheckoutEvent.observe(viewLifecycleOwner, EventObserver{
            val customDialog = CustomDialog.newInstancePaymentDialog(requireContext()).apply {
                passClickPayEvent(cartViewmodel)
            }
            customDialog.show()
        })

        cartViewmodel.clickPayZalo.observe(this, EventObserver{
            payZalo()
        })
    }

    fun payZalo(){
        val orderApi = CreateOrder()
        try {
            val data = orderApi.createOrder("100000")
            val code = data.getString("return_code")
            Log.e("code",code)
            if (code == "1") {
                val token = data.getString("zp_trans_token")
                Log.e("token",token)

                ZaloPaySDK.getInstance().payOrder(
                    requireActivity(),
                    token,
                    "demozpdk://app",
                    object : PayOrderListener {
                        override fun onPaymentSucceeded(
                            transactionId: String,
                            transToken: String,
                            appTransID: String
                        ) {
                            activity?.runOnUiThread(Runnable {
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Payment Success")
                                    .setMessage(
                                        String.format(
                                            "TransactionId: %s - TransToken: %s",
                                            transactionId,
                                            transToken
                                        )
                                    )
                                    .setPositiveButton("OK",
                                        DialogInterface.OnClickListener { dialog, which -> })
                                    .setNegativeButton("Cancel", null).show()
                            })
                        }

                        override fun onPaymentCanceled(zpTransToken: String, appTransID: String) {
                            AlertDialog.Builder(requireContext())
                                .setTitle("User Cancel Payment")
                                .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                                .setPositiveButton("OK",
                                    DialogInterface.OnClickListener { dialog, which -> })
                                .setNegativeButton("Cancel", null).show()
                        }

                        override fun onPaymentError(
                            zaloPayError: ZaloPayError,
                            zpTransToken: String,
                            appTransID: String
                        ) {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Payment Fail")
                                .setMessage(
                                    String.format(
                                        "ZaloPayErrorCode: %s \nTransToken: %s",
                                        zaloPayError.toString(),
                                        zpTransToken
                                    )
                                )
                                .setPositiveButton("OK",
                                    DialogInterface.OnClickListener { dialog, which -> })
                                .setNegativeButton("Cancel", null).show()
                            Log.e("Code",zaloPayError.name)
                        }
                    })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}