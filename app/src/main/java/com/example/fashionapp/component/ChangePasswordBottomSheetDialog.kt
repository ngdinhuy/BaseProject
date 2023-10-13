package com.example.fashionapp.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fashionapp.R
import com.example.fashionapp.databinding.BottomSheetChangePasswordBinding
import com.example.fashionapp.utils.Utils
import com.example.fashionapp.utils.makeToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangePasswordBottomSheetDialog : BottomSheetDialogFragment() {
    lateinit var databinding: BottomSheetChangePasswordBinding
    var clickEvent : ClickEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = BottomSheetChangePasswordBinding.inflate(inflater, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databinding.btSave.setOnClickListener {
            val oldPassword = databinding.etOldPassword.text.toString()
            val newPassword = databinding.etNewPassword.text.toString()
            val repeatPassword = databinding.etRepeatPassword.text.toString()

            if (Utils.validatePassword(newPassword) != null) {
                requireContext().makeToast(Utils.validatePassword(newPassword) ?: "")

            } else if (newPassword != repeatPassword) {
                requireContext().makeToast("Passwords do not match")
            } else {
                clickEvent?.clickSave(oldPassword, newPassword)
                this@ChangePasswordBottomSheetDialog.dismiss()
            }
        }
    }
}
interface ClickEvent{
    fun clickSave(oldPassword: String, newPassword: String)
}