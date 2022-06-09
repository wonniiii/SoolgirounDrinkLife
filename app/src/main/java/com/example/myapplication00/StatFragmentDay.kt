package com.example.myapplication00

import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.projectapp.DBHelper
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class StatFragmentDay : Fragment() {


    lateinit var DBHelper: DBHelper
    lateinit var activity: Activity

    val cal = Calendar.getInstance()
    val nowMonth = cal.get(Calendar.MONTH)+1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        DBHelper = DBHelper(activity)
        val root = inflater.inflate(R.layout.fragment_stat_day, container, false)


        val barChart: BarChart = root.findViewById(R.id.barChart)
        val entries = ArrayList<BarEntry>()
        var x = 1.2f

        val array: ArrayList<Int> = DBHelper.getAlcohol(nowMonth)

        for (i in array.reversed()) {
            entries.add(BarEntry(x,array.get(i).toFloat()))
            x+=1
        }

        barChart.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
            setMaxVisibleValueCount(7) // 최대 보이는 그래프 개수를 7개로 지정
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawBarShadow(false) //그래프의 그림자
            setDrawGridBackground(false)//격자구조 넣을건지
            axisLeft.run { //왼쪽 축. 즉 Y방향 축을 뜻한다.
                axisMaximum =
                    (Collections.max(array).toFloat()+5.0).toFloat() //20 위치에 선을 그리기 위해 21f로 맥시멈값 설정
                axisMinimum = 0f // 최소값 0
                granularity = 2f // 2 단위마다 선을 그리려고 설정.
                setDrawLabels(true) // 값 적는거 허용 (0, 2, 4)
                setDrawGridLines(true) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                axisLineColor = ContextCompat.getColor(context, R.color.line) // 축 색깔 설정
                gridColor = ContextCompat.getColor(context, R.color.line) // 축 아닌 격자 색깔 설정
                textColor = ContextCompat.getColor(context, R.color.pink) // 라벨 텍스트 컬러 설정
                textSize = 13f //라벨 텍스트 크기
            }
            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에다가 둔다.
                granularity = 1f // 1 단위만큼 간격 두기
                setDrawAxisLine(true) // 축 그림
                setDrawGridLines(false) // 격자
                textColor = ContextCompat.getColor(context, R.color.pink) //라벨 색상
                textSize = 12f // 텍스트 크기
//                valueFormatter =
//                    StatFragmentDay.MyXAxisFormatter() // X축 라벨값(밑에 표시되는 글자) 바꿔주기 위해 설정
            }
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            xAxis.isEnabled = false
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(2000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }

        var set = BarDataSet(entries, "DataSet") // 데이터셋 초기화
        set.color = ContextCompat.getColor(requireContext(), R.color.pink) // 바 그래프 색 설정
        val dataSet: ArrayList<IBarDataSet> = ArrayList()

        dataSet.add(set)
        val data = BarData(dataSet)
        data.barWidth = 0.3f //막대 너비 설정
        barChart.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            setFitBars(true)
            invalidate()
        }


        return root
    }

    class MyXAxisFormatter : ValueFormatter() {
        private val days = arrayOf("1일", "2일", "3일", "4일", "5일", "6일", "7일")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt() - 1) ?: value.toString()
        }

    }



}

