package com.example.baseproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.baseproject.ui.dialog.DialogProgressLoading
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewmodel> : Fragment() {

    lateinit var vm: VM
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    private var dialogProgressLoading: DialogProgressLoading? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = provideViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = provideViewBinding(inflater, container)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setUpLoadingEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.isLoading.collect {
                if (it) {
                    if (dialogProgressLoading == null) {
                        dialogProgressLoading = DialogProgressLoading.show(childFragmentManager, true)
                    }
                } else {
                    dialogProgressLoading?.dismiss()
                    dialogProgressLoading = null
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
