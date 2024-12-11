package com.palash.luckylottery.fragments.dealer_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.palash.luckylottery.adapter.ViewPagerAdapter
import com.palash.luckylottery.databinding.FragmentFavBuyTicketListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DealerPortalFragment : Fragment() {
    private var _binding: FragmentFavBuyTicketListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavBuyTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager2 = binding.viewPager2
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(this)

        viewPager2.adapter = adapter

        // Link TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            if (position == 0) {
                tab.text = "Sub dealer"
            } else if (position == 1) {
                tab.text = "Earn Money"
            } else {
                tab.text = "Booked ticket"
            }

        }.attach()
    }
}