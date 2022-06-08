package com.example.myapplication00

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class StatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_stat, container, false)
        val viewpager = root.findViewById<ViewPager2>(R.id.viewpager)
        val tablayout = root.findViewById<TabLayout>(R.id.tabLayout)
        val textarr = arrayListOf<String>("일 음주량","월 주종","월별 비교","칼로리")
        viewpager.adapter = StatViewPagerAdatper(this)

        TabLayoutMediator(tablayout, viewpager) {
                tab,position ->
            tab.text = textarr[position]
        }.attach()
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

}