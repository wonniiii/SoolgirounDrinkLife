package com.example.mpproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mpproject.databinding.ActivityStatisticsBinding
import com.google.android.material.tabs.TabLayoutMediator

class StatisticsActivity : AppCompatActivity() {
    lateinit var binding:ActivityStatisticsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.viewpager.adapter = StatisticsViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewpager) {
            tab,position ->

        }.attach()
    }
}