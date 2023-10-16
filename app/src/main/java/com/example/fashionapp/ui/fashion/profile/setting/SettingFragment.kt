package com.example.fashionapp.ui.fashion.profile.setting

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.fashionapp.component.ChangePasswordBottomSheetDialog
import com.example.fashionapp.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class SettingFragment:Fragment(), DatePickerDialog.OnDateSetListener{
    lateinit var databinding : FragmentSettingBinding
    val viewmodel : SettingViewmodel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentSettingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@SettingFragment.viewmodel
        }
        return databinding.root
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.getInfo()

        setUpEvent()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setUpEvent() {
        viewmodel.clickChangePasswordEvent.observe(viewLifecycleOwner, Observer {
            val changePasswordBottomSheetDialog = ChangePasswordBottomSheetDialog().apply {
                this.clickEvent = viewmodel
            }.show(activity?.supportFragmentManager!!, "")
        })

        databinding.etDob.setOnClickListener {
            showDatePickerDialog(viewmodel.dob.value)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDatePickerDialog(date: String?){
        var year = 0
        var month = 0
        var day = 0
        if (date.isNullOrBlank()){
            val today = Calendar.getInstance()
            year = today.get(Calendar.YEAR)
            month = today.get(Calendar.MONTH)
            day = today.get(Calendar.DAY_OF_MONTH)
        } else {
            val dates = date.split("/")
            if (dates.size > 2 ){
                year = dates[2].toInt()
                month = dates[1].toInt()
                day = dates[0].toInt()
            }
        }
        val datePickerDialog = DatePickerDialog(requireContext()).apply {
            setOnDateSetListener(this@SettingFragment)
            updateDate(year, month, day)
        }.show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        viewmodel.dob.value = "$p3/${p2+1}/$p1"
    }
}