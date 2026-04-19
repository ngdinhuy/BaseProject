package com.example.baseproject.ui.camera

import android.Manifest
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.databinding.FragmentCameraBinding
import com.example.baseproject.utils.CheckPermissionHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraFragment: BaseFragment<FragmentCameraBinding, CameraViewmodel>() {
    val viewmodel : CameraViewmodel by viewModels()

    private val launcher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(requireContext(),
                    "Permission request denied",
                    Toast.LENGTH_SHORT).show()
            } else {
                startCamera()
            }
        }

    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCameraBinding {
        return FragmentCameraBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): CameraViewmodel {
        return viewmodel
    }

    override fun setUpView() {

    }

    override fun setUpEvent() {
        super.setUpEvent()
        requestPermission()
    }

    fun requestPermission() {
        if (!CheckPermissionHelper.hasPermissions(requireContext(), REQUIRED_PERMISSIONS)) {
            launcher.launch(REQUIRED_PERMISSIONS.toTypedArray())
        } else {
            startCamera()
        }
    }

    private fun startCamera() {

    }


    companion object {
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
    }
}