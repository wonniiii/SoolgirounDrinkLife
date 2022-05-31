package com.example.mpproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class StatisticsViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0->StatisticsFragmentWeek()
            1->StatisticsFragmentMonth()
            2->StatisticsFragment6Month()
            else->StatisticsFragmentYear()
        }
    }

}