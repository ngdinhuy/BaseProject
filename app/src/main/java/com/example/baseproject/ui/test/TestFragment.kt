package com.example.baseproject.ui.test

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.baseproject.base.BaseFragment
import com.example.baseproject.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : BaseFragment<FragmentTestBinding, TestViewmodel>() {

    private val viewmodel: TestViewmodel by viewModels()
    private lateinit var pagerAdapter: TestPagerAdapter

    private val testPages = listOf(
        TestPagerItem("Page 1", Color.parseColor("#FF5722")),
        TestPagerItem("Page 2", Color.parseColor("#4CAF50")),
        TestPagerItem("Page 3", Color.parseColor("#2196F3")),
        TestPagerItem("Page 4", Color.parseColor("#9C27B0")),
        TestPagerItem("Page 5", Color.parseColor("#FF9800")),
        TestPagerItem("Page 6", Color.parseColor("#00BCD4")),
        TestPagerItem("Page 7", Color.parseColor("#E91E63"))
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun provideViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTestBinding {
        return FragmentTestBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): TestViewmodel {
        return viewmodel
    }

    override fun setUpView() {
        setupViewPager()
    }

    private fun setupViewPager() {
        pagerAdapter = TestPagerAdapter(testPages)
        binding!!.viewPager.adapter = pagerAdapter

        updatePageInfo(0)

        binding!!.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updatePageInfo(position)
            }
        })
        binding!!.dotsIndicator.attachViewpager(binding!!.viewPager)
    }

    private fun updatePageInfo(position: Int) {
        binding!!.tvPageInfo.text = "Page ${position + 1} / ${testPages.size}"
    }

    override fun setUpEvent() {
        super.setUpEvent()

        viewmodel.initDecryptHelper()
    }
}
