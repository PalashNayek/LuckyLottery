package com.palash.luckylottery.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.palash.luckylottery.fragments.dealer_fragment.AddSubDealerFragment
import com.palash.luckylottery.fragments.dealer_fragment.DealerBuyTicketListFragment
import com.palash.luckylottery.fragments.dealer_fragment.EarnMoneyFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3 // Number of pages

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AddSubDealerFragment()
            1 -> EarnMoneyFragment()
            2 -> DealerBuyTicketListFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}