package com.example.baseproject.ui.dialog

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.baseproject.R

class DialogProgressLoading : DialogFragment() {

    companion object {
        fun show(fm: FragmentManager,cancelable: Boolean = true): DialogProgressLoading {
            val dialog = DialogProgressLoading()
            dialog.isCancelable = cancelable
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Dialog)
            dialog.show(fm,"DialogProgressLoading")
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.fragment_loading,container,false)
    }
}