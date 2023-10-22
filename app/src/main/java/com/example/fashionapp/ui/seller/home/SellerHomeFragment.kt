package com.example.fashionapp.ui.seller.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.androidplot.xy.BarFormatter
import com.androidplot.xy.CatmullRomInterpolator
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.PanZoom
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.androidplot.xy.XYSeries
import com.example.fashionapp.R
import com.example.fashionapp.databinding.FragmentSellerHomeBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.Arrays

@AndroidEntryPoint
class SellerHomeFragment : Fragment(){
    lateinit var databinding : FragmentSellerHomeBinding
    val viewmodel by viewModels<SellerHomeViewmodel> ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = FragmentSellerHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@SellerHomeFragment.viewmodel
        }
        return databinding.root
    }

    override fun onResume() {
        super.onResume()
        viewmodel.getInfoUser()
        viewmodel.getStatisticIncome()
        viewmodel.getStatCurrentMonth()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpEvent()
    }

    private fun setUpView() {
        databinding.statRevenue.apply {
            tvTitle.text = "Revenue"
            tvContent.text = "This month's revenue statistics"
            ivContent.setImageResource(R.drawable.ic_income_seller)
        }

        databinding.statOrder.apply {
            tvTitle.text = "Order"
            tvContent.text = "This month's order statistics"
            ivContent.setImageResource(R.drawable.ic_order_seller)
        }

        databinding.statRate.apply {
            tvTitle.text = "Review"
            tvContent.text = "Review statistics"
            ivContent.setImageResource(R.drawable.ic_rate_seller)
        }
    }

    private fun setUpEvent() {
        viewmodel.statisticIncome.observe(viewLifecycleOwner, Observer {
            databinding.statRevenue.tvStatistic.text = it
        })
        viewmodel.statisticOrder.observe(viewLifecycleOwner, Observer {
            databinding.statOrder.tvStatistic.text = it
        })
        viewmodel.statisticRate.observe(viewLifecycleOwner, Observer {
            databinding.statRate.tvStatistic.text = it
        })

        viewmodel.statistic.observe(viewLifecycleOwner, Observer {
            setUpChart(it)
        })
    }

    fun setUpChart(map:MutableMap<String, Double>){
        val entries = ArrayList<BarEntry>()
        val data = map.values.toList()
        var max = 0f
        for (i in data.indices){
            entries.add(BarEntry(i.toFloat(), data[i].toFloat()))
            if (data[i].toFloat()>max){
                max = data[i].toFloat()
            }
        }

        val yAxis = databinding.barchart.axisLeft
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = max
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = Color.RED

        val dataSet = BarDataSet(entries, "$")
        dataSet.setColor(Color.RED)

        val barData = BarData(dataSet)
        databinding.barchart.data = barData
        databinding.barchart.description.isEnabled = false
        databinding.barchart.invalidate()

        databinding.barchart.apply {
            xAxis.valueFormatter = IndexAxisValueFormatter(map.keys.toList())
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true
        }
    }
}