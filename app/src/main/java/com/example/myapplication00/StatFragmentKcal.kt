package com.example.myapplication00

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.projectapp.DBHelper
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarEntry


class StatFragmentKcal : Fragment() {

    lateinit var DBHelper: DBHelper
    lateinit var activity : Activity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_stat_kcal, container, false)

        DBHelper = DBHelper(activity)
        val array = DBHelper.getAllAlcohol()

        val soKcal = array[0]*64
        val macKcal = array[1]*96
        val makKcal = array[2]*99
        val wineKcal = array[3]*70

        val totalKcal = soKcal+macKcal+makKcal+wineKcal

        val kcal = root.findViewById<TextView>(R.id.kcal)
        kcal.text = totalKcal.toString()


        return root
    }

}