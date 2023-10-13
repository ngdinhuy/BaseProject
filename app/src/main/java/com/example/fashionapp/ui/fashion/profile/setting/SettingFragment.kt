package com.example.fashionapp.ui.fashion.profile.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.fashionapp.component.ChangePasswordBottomSheetDialog
import com.example.fashionapp.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment:Fragment() {
    lateinit var databinding : FragmentSettingBinding
    val viewmodel : SettingViewmodel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentSettingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@SettingFragment.viewmodel
        }
        return databinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.getInfo()

        setUpEvent()
    }

    private fun setUpEvent() {
        viewmodel.clickChangePasswordEvent.observe(viewLifecycleOwner, Observer {
            val changePasswordBottomSheetDialog = ChangePasswordBottomSheetDialog().apply {
                this.clickEvent = viewmodel
            }.show(activity?.supportFragmentManager!!, "")
        })
    }
}