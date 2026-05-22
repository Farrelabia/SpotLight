package com.example.spotlight.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AboutPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = listOf(
        com.example.spotlight.fragment.AboutAppFragment::class.java,
        com.example.spotlight.fragment.ProfileFragment::class.java
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position].newInstance()
    }
}
