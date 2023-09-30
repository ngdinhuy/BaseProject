package com.example.fashionapp.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fashionapp.databinding.BottomSheetFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet: BottomSheetDialogFragment() {
    lateinit var databinding : BottomSheetFilterBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databinding.optionPriceLowestToHigh.tvContentFilter.apply {
            text = "HUynd"
            isEnabled = true
        }


    }
}