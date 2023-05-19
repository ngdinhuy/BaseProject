package com.example.fashionapp.ui.fashion.detail_product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fashionapp.Define
import com.example.fashionapp.R
import com.example.fashionapp.databinding.FragmentDetailProductBinding
import com.example.fashionapp.utils.EventObserver
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductFragment: Fragment() {
    lateinit var databinding: FragmentDetailProductBinding
    val viewmodel : DetailProductViewmodel by viewModels()
    val args: DetailProductFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentDetailProductBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            product = args.product
            viewmodel = this@DetailProductFragment.viewmodel
        }
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEvent()
    }

    private fun setUpEvent() {
        viewmodel.eventBack.observe(this, EventObserver{
            findNavController().popBackStack()
        })

        viewmodel.likeEvent.observe(viewLifecycleOwner, EventObserver{
            if (it){
                var exist = true
                Define.listLikeItem.forEach{
                    if (it == args.product!!._id){
                        exist = false
                    }
                }
                if (exist){
                    Define.listLikeItem.add(args.product!!._id!!)
                }
                databinding.imgLike.setImageResource(R.drawable.ic_like_product)
            }else{
                databinding.imgLike.setImageResource(R.drawable.ic_unlike_product)
            }
        })
    }
}