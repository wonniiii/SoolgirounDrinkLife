package com.example.mpproject

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class StatisticsFragmentType : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_statistics_type, container, false)

        val pieChart: PieChart = root.findViewById(R.id.pieChart)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(20.0f, "소주"))
        entries.add(PieEntry(10.5f, "맥주"))
        entries.add(PieEntry(5.0f, "양주"))
        entries.add(PieEntry(11.0f, "칵테일"))

        val colorItems = ArrayList<Int>()
        for(c in ColorTemplate.VORDIPLOM_COLORS) colorItems.add(c)
        for(c in ColorTemplate.JOYFUL_COLORS) colorItems.add(c)
        for(c in ColorTemplate.COLORFUL_COLORS) colorItems.add(c)
        for(c in ColorTemplate.LIBERTY_COLORS) colorItems.add(c)
        for(c in ColorTemplate.PASTEL_COLORS) colorItems.add(c)
        colorItems.add(ColorTemplate.getHoloBlue())

        val dataSet: PieDataSet = PieDataSet(entries, "주종")
        dataSet.apply {
            colors = colorItems
            valueTextColor = Color.BLACK
            valueTextSize = 14f
        }


        val pieData: PieData = PieData(dataSet)
        pieChart.apply{
            data = pieData
            description.isEnabled = false
            isRotationEnabled = false
            centerText = "한달 내 마신 술 종류"
            setEntryLabelColor(Color.BLACK)
            animateY(2000)
            animate()


        }

        return  root
    }



}