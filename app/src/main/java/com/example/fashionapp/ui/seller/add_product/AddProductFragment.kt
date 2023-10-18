package com.example.fashionapp.ui.seller.add_product

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fashionapp.Define
import com.example.fashionapp.component.ClickButtonEvent
import com.example.fashionapp.component.DialogNumberPicker
import com.example.fashionapp.databinding.FragmentSellerAddProductBinding
import com.example.fashionapp.utils.Utils
import com.example.fashionapp.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment: Fragment(), ClickButtonEvent {
    lateinit var databinding : FragmentSellerAddProductBinding
    val viewmodel : AddProductViewmodel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentSellerAddProductBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@AddProductFragment.viewmodel
        }
        return databinding.root
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getListCategory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvent()
    }

    private fun setUpEvent() {
        databinding.rlCategory.setOnClickListener {
            val dialog = DialogNumberPicker().apply {
                listCategory = viewmodel.listCategory
                clickButtonEvent = this@AddProductFragment
            }.show(requireFragmentManager(), "")
        }

        databinding.rlCategory.setOnClickListener {
            if (Utils.checkListPermission(requireContext(), Define.listPermission)) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 1)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), Define.listPermission.toTypedArray(), 0)
            }
        }

        databinding.rlCategory.setOnClickListener {
            if (Utils.checkListPermission(requireContext(), Define.listPermission)) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 0)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), Define.listPermission.toTypedArray(), 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data") as? Bitmap
            image?.let {
                val realPath = Utils.getImageUri(requireContext(),it)
//                viewmodel.avatar.value = Utils.getPathFromUri(requireContext(), Uri.parse(realPath))
//                viewmodel.isChangeAvatar = true
            }
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            uri?.let {
                val realPath = Utils.getPathFromUri(requireContext(), it)
//                viewmodel.avatar.value = realPath
//                viewmodel.isChangeAvatar = true
            }
        }
    }


    override fun clickOk(id: Int) {
        viewmodel.setUpType(id)
    }
}