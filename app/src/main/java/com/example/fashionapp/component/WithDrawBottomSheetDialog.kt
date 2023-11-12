package com.example.fashionapp.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.fashionapp.R
import com.example.fashionapp.databinding.BottomSheetWithdrawBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WithDrawBottomSheetDialog: BottomSheetDialogFragment() {
    lateinit var databinding: BottomSheetWithdrawBinding
    var money: String? = null
    var mailAcccountPaypal: String? = null
    var clickEvent: WithdrawEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = BottomSheetWithdrawBinding.inflate(inflater, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        databinding.apply {
            cbMoney.text = "Full money: $money"
            mailAcccountPaypal?.let {
                etPaypalAccount.setText(it)
            }
            cbMoney.setOnCheckedChangeListener { p0, p1 ->
                if (p1){
                    etMoney.setText(money)
                }
            }

            btWithdraw.setOnClickListener {
                clickEvent?.withdraw(etMoney.text.toString(),
                    etPaypalAccount.text.toString(),
                    etPassword.text.toString(),
                    cbSave.isChecked)
                this@WithDrawBottomSheetDialog.dismiss()
            }
        }
    }
}
interface WithdrawEvent{
    fun withdraw(money: String, mailPaypal: String, password: String, isSaveAccount: Boolean)
}