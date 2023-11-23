package com.example.fashionapp.ui.fashion.review

import android.media.metrics.Event
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fashionapp.databinding.FragmentReviewBinding
import com.example.fashionapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment: Fragment() {
    val viewmodel: ReviewViewmodel by viewModels()
    val navArgs : ReviewFragmentArgs by navArgs()
    lateinit var databinding : FragmentReviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentReviewBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@ReviewFragment.viewmodel.apply {
                idOrderItem = navArgs.idOrderItem
            }
        }
        return databinding.root
    }

    override fun onResume() {
        super.onResume()
        viewmodel.loadOrderItem()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpEvent()
    }

    private fun setUpEvent() {
        databinding.tvBack.setOnClickListener {
            findNavController().popBackStack()
        }

        databinding.btnCheckout.setOnClickListener {
            viewmodel.clickRate(databinding.ratingBar.rating.toInt())
        }

        viewmodel.eventBack.observe(viewLifecycleOwner, EventObserver{
            findNavController().popBackStack()
        })
    }
}