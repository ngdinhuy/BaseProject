package com.example.baseproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.baseproject.ui.dialog.DialogProgressLoading
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewmodel> : Fragment() {

    lateinit var vm: VM
    protected var binding: VB? = null

    private var dialogProgressLoading : DialogProgressLoading? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = provideViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = provideViewBinding(inflater, container)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpEvent()
    }

    fun setUpLoadingEvent() {
        lifecycleScope.launch {
            vm.isLoading.collect {
                if(it) {
                    if (dialogProgressLoading == null) {
                        dialogProgressLoading = DialogProgressLoading()
                    }
                    dialogProgressLoading = DialogProgressLoading.show(childFragmentManager, true)
                } else {
                    dialogProgressLoading?.dismiss()
                }
            }
        }
    }

    abstract fun provideViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun provideViewModel(): VM

    abstract fun setUpView()

    open fun setUpEvent() {
        setUpLoadingEvent()
    }
}