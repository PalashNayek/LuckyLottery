package com.palash.luckylottery.fragments.dealer_fragment

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.palash.luckylottery.R
import com.palash.luckylottery.adapter.DealerHomeAdapter
import com.palash.luckylottery.databinding.FragmentDealerHomeBinding
import com.palash.luckylottery.models.LotteryHomeResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DealerHomeFragment : Fragment() {
    private var _binding: FragmentDealerHomeBinding? = null
    private val binding get() = _binding!!

    private var textLabel: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDealerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        val searchView = binding.searchView
        textLabel = binding.labelText
        val searchHintTextView =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        textLabel!!.visibility = View.VISIBLE
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {

                animateHintColor(
                    searchHintTextView,
                    Color.parseColor("#757575"),
                    Color.parseColor("#FF0000")
                ) // Light gray to red
                textLabel!!.visibility = View.GONE
            } else {

                animateHintColor(
                    searchHintTextView,
                    Color.parseColor("#FF0000"),
                    Color.parseColor("#757575")
                ) // Red to light gray
                textLabel!!.visibility = View.VISIBLE
            }

        }

        fun onLotteryClicked(lotteryHomeResponse: LotteryHomeResponse) {
            //Toast.makeText(requireContext(), "${lotteryHomeResponse.ticketNumber} clicked", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_dealerHomeFragment_to_dealerPaymentFragment)
        }

        val adapter = DealerHomeAdapter(::onLotteryClicked)

        val p1 = LotteryHomeResponse(102030, "40264", false)
        val p2 = LotteryHomeResponse(102031, "59361", false)
        val p3 = LotteryHomeResponse(102032, "56074", true)
        val p4 = LotteryHomeResponse(102033, "63089", false)
        val p5 = LotteryHomeResponse(102040, "95371", true)

        adapter.submitList(
            listOf(p1, p2, p3, p4, p5)
        )//adapter.submitList(it.data)

        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)//LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun animateHintColor(editText: EditText, startColor: Int, endColor: Int) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
        colorAnimation.duration = 300 // Duration in milliseconds
        colorAnimation.addUpdateListener { animator ->
            editText.setHintTextColor(animator.animatedValue as Int)
        }

        colorAnimation.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}