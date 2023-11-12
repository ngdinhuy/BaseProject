package com.example.fashionapp.ui.fashion.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fashionapp.Role
import com.example.fashionapp.component.ChangePasswordBottomSheetDialog
import com.example.fashionapp.component.WithDrawBottomSheetDialog
import com.example.fashionapp.component.WithdrawEvent
import com.example.fashionapp.data.ui.auth.splash.SplashFragmentDirections
import com.example.fashionapp.databinding.FragmentProfileBinding
import com.example.fashionapp.ui.fashion.FashionViewmodel
import com.example.fashionapp.ui.seller.SellerViewmodel
import com.example.fashionapp.ui.seller.home.SellerHomeViewmodel
import com.example.fashionapp.utils.EventObserver
import com.example.fashionapp.utils.Prefs
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment(){
    lateinit var databinding: FragmentProfileBinding
    val viewmodel by viewModels<ProfileViewmodel> ()
    val fashionViewmodel_ by viewModels<FashionViewmodel>(ownerProducer = { requireParentFragment().requireParentFragment() })
    val sellerViewmodel by viewModels<SellerViewmodel>(ownerProducer = { requireParentFragment().requireParentFragment() })
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentProfileBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@ProfileFragment.viewmodel.apply {
                fashionViewmodel = fashionViewmodel_
                sellerViewmodel = this@ProfileFragment.sellerViewmodel
            }
        }
        return databinding.root
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getInfoUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpEvent()
    }

    private fun setUpEvent() {
        viewmodel.userInfoResponse.observe(viewLifecycleOwner, Observer {
            databinding.userInfo = it
        })

        viewmodel.clickChangePasswordEvent.observe(viewLifecycleOwner, EventObserver {
            val changePasswordBottomSheetDialog = ChangePasswordBottomSheetDialog().apply {
                this.clickEvent = viewmodel
            }.show(activity?.supportFragmentManager!!, "")
        })

        databinding.flMoney.setOnClickListener {
            WithDrawBottomSheetDialog().apply {
                viewmodel.userInfoResponse.value?.let {
                    money = it.property
                    mailAcccountPaypal = it.mailPaypal
                    clickEvent = viewmodel
                }
            }.show(activity?.supportFragmentManager!!,"")
        }
    }


    private fun setUpView() {
        val isSeller = Prefs.newInstance(requireContext()).getRole() == Role.SELLER
        databinding.flMyOrder.isVisible = !isSeller
        databinding.viewSeparateOrder.isVisible = !isSeller
        databinding.flMoney.isVisible = isSeller
    }
}