package com.example.fashionapp.ui.fashion.profile.setting

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fashionapp.Define
import com.example.fashionapp.databinding.FragmentSettingBinding
import com.example.fashionapp.utils.EventObserver
import com.example.fashionapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class SettingFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    lateinit var databinding: FragmentSettingBinding
    val viewmodel: SettingViewmodel by viewModels()


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
        databinding.etDob.setOnClickListener {
            showDatePickerDialog(viewmodel.dob.value)
        }

        databinding.avatar.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Pick pictures from your camera or gallery.")
                .setPositiveButton("Gallery") { p0, p1 ->
                    if (Utils.checkListPermission(requireContext(), Define.listPermission)) {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent, 1)
                    } else {
                        ActivityCompat.requestPermissions(requireActivity(), Define.listPermission.toTypedArray(), 0)
                    }
                }
                .setNegativeButton("Camera") { p0, _ ->
                    if (Utils.checkListPermission(requireContext(), Define.listPermission)) {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, 0)
                    } else {
                        ActivityCompat.requestPermissions(requireActivity(), Define.listPermission.toTypedArray(), 0)
                    }
                }
                .show()
        }

        viewmodel.backEvent.observe(viewLifecycleOwner, EventObserver{
            findNavController().popBackStack()
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDatePickerDialog(date: String?) {
        var year = 0
        var month = 0
        var day = 0
        if (date.isNullOrBlank()) {
            val today = Calendar.getInstance()
            year = today.get(Calendar.YEAR)
            month = today.get(Calendar.MONTH)
            day = today.get(Calendar.DAY_OF_MONTH)
        } else {
            val dates = date.split("/")
            if (dates.size > 2) {
                year = dates[2].toInt()
                month = dates[1].toInt()
                day = dates[0].toInt()
            }
        }
        DatePickerDialog(requireContext()).apply {
            setOnDateSetListener(this@SettingFragment)
            updateDate(year, month, day)
        }.show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        viewmodel.dob.value = "$p3/${p2 + 1}/$p1"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK) {
            val image = data?.extras?.get("data") as? Bitmap
            image?.let {
                val realPath = Utils.getImageUri(requireContext(),it)
                viewmodel.avatar.value = Utils.getPathFromUri(requireContext(), Uri.parse(realPath))
                viewmodel.isChangeAvatar = true
            }
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val uri = data?.data
            uri?.let {
                val realPath = Utils.getPathFromUri(requireContext(), it)
                viewmodel.avatar.value = realPath
                viewmodel.isChangeAvatar = true
            }
        }
    }

}