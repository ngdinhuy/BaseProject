package com.example.baseproject.ui.auth.images

import android.Manifest
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.databinding.FragmentImagesBinding
import com.example.baseproject.extensions.hasPermissionsAccessImage
import com.example.baseproject.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.collections.component1
import kotlin.collections.component2

@AndroidEntryPoint
class ImagesFragment: BaseFragment<FragmentImagesBinding, ImagesViewModel>() {
    val viewmodel: ImagesViewModel by viewModels <ImagesViewModel>()
    val adapter: ImageAdapter by lazy {
        ImageAdapter().apply {
            clickListener = object : ClickListener {
                override fun onClickListener(id: Long) {
                    val action = ImagesFragmentDirections.actionImagesFragmentToDetailImageFragment(id)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private val requestPermissionSearchLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                setUpListImage()
                adapter.refresh()
            }
        }

    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentImagesBinding {
        return FragmentImagesBinding.inflate(inflater, container, false).apply {}
    }

    override fun provideViewModel(): ImagesViewModel {
        return viewmodel
    }

    override fun setUpView() {
        if (requireContext().hasPermissionsAccessImage()) {
            setUpListImage()
        } else {
            requestPermissionSearchLauncher.launch(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            })
        }

        binding?.sw?.isChecked = Utils.checkSystemIsDarkMode(requireContext())
        binding?.sw?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setUpListImage() {
        binding?.rvImages?.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = this@ImagesFragment.adapter
        }
    }

    override fun setUpEvent() {
        super.setUpEvent()
        lifecycleScope.launch {
            viewmodel.flow.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}