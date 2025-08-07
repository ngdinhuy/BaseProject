package com.example.baseproject.ui.auth.detail_image

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.databinding.FragmentDetailImageBinding
import com.example.baseproject.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class DetailImageFragment : BaseFragment<FragmentDetailImageBinding, DetailImageViewModel>(){
    val viewmodel : DetailImageViewModel by viewModels()
    val navArgs by navArgs<DetailImageFragmentArgs>()

    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailImageBinding {
        return FragmentDetailImageBinding.inflate(inflater, container, false).apply {
            viewmodel = this@DetailImageFragment.viewmodel
        }
    }

    override fun provideViewModel(): DetailImageViewModel {
        return viewmodel
    }

    override fun setUpView() {
        viewmodel.queryDetailImage(navArgs.idImage)
    }

    override fun setUpEvent() {
        super.setUpEvent()
        lifecycleScope.launch {
            viewmodel.detailImage.collectLatest { imageModel ->
                imageModel?.let {
                    binding?.ivImage?.setImageURI(it.uri)
                    it.dateAdded?.let { dateAdded ->
                        binding?.tvDate?.text = Utils.formatMillisToVietnameseTime(dateAdded)
                    } ?: run {
                        binding?.tvDate?.isVisible = false
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewmodel.backEvent.collectLatest {
                findNavController().navigateUp()
            }
        }
    }
}