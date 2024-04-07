package com.example.baseproject.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding, ViewModel : BaseViewmodel> : Fragment() {
    
}