package com.example.fashionapp.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fashionapp.Define
import com.example.fashionapp.R
import com.example.fashionapp.databinding.BottomSheetFilterBinding
import com.example.fashionapp.utils.makeToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheetDialog: BottomSheetDialogFragment() {
    lateinit var databinding : BottomSheetFilterBinding
    var onFilterClick : OnFilterClick? = null
    var filter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databinding.optionCustomerReview.tvContentFilter.apply {
            text = Define.listFilterText[1]
            if (filter == 1)
                isSelected = true
            setOnClickListener {
                onFilterClick?.filterClick(1)
                this@FilterBottomSheetDialog.dismiss()
            }
        }

        databinding.optionPriceLowestToHigh.tvContentFilter.apply {
            text = Define.listFilterText[2]
            if (filter == 2)
                isSelected = true
            setOnClickListener{
                onFilterClick?.filterClick(2)
                this@FilterBottomSheetDialog.dismiss()
            }
        }

        databinding.optionPriceHighestToLow.tvContentFilter.apply {
            text = Define.listFilterText[3]
            if (filter == 3)
                isSelected = true
            setOnClickListener{
                onFilterClick?.filterClick(3)
                this@FilterBottomSheetDialog.dismiss()
            }
        }
    }
}
interface OnFilterClick{
    fun filterClick(filter:Int)
}