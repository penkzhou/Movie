package com.oldautumn.movie.ui.main.explore

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ExploreViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            ExploreTrendingTabFragment()
        } else {
            ExplorePopularTabFragment()
        }
    }
}
