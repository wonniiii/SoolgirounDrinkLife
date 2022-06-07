package com.example.mpproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.mpproject.databinding.ActivityStatisticsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class StatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        initLayout()
    }

    private fun initLayout() {
        val viewpager = findViewById<ViewPager2>(R.id.viewpager)
        val tablayout = findViewById<TabLayout>(R.id.tabLayout)
        val textarr = arrayListOf<String>("일 음주량","월 주종","월별 비교","칼로리")
        viewpager.adapter = StatisticsViewPagerAdapter(this)
        TabLayoutMediator(tablayout, viewpager) {
            tab,position ->
            tab.text = textarr[position]
        }.attach()
    }
}