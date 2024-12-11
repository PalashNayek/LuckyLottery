package com.palash.luckylottery.fragments.customar_fragment

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
import androidx.recyclerview.widget.GridLayoutManager
import com.palash.luckylottery.adapter.CustomerHomeAdapter
import com.palash.luckylottery.databinding.FragmentCustomarHomeBinding
import com.palash.luckylottery.models.LotteryHomeResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomarHomeFragment : Fragment() {
    private var _binding: FragmentCustomarHomeBinding? = null
    private val binding get() = _binding!!
    private var textLabel: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomarHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        //search.....................
        val searchView = binding.searchView
        textLabel = binding.labelText

        // Get the hint TextView inside the SearchView
        val searchHintTextView =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        //searchHintTextView.setHintTextColor(Color.parseColor("#757575")) // Light gray
        //searchHintTextView.setTextColor(Color.parseColor("#000000")) // Black text

        // Optional: Add custom hint

        // Inside setOnQueryTextFocusChangeListener
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

        val adapter = CustomerHomeAdapter()

        val p1 = LotteryHomeResponse(10551, "40033", false)
        val p2 = LotteryHomeResponse(30123, "99193", true)

        adapter.submitList(
            listOf(p1, p2)
        )//adapter.submitList(it.data)


        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2)//LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        /*//scenario, after 4 sec list updated ..........
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val p3 = ProgrammingItem(3,"A", "Android")
            val p4 = ProgrammingItem(4,"R", "Rust")
            val p5 = ProgrammingItem(5,"G", "GoLang")
            val p6 = ProgrammingItem(6,"N", "Node")
            val p7 = ProgrammingItem(7,"P", "Python")

            adapter.submitList(listOf(p3,p4,p5,p6,p7))
        },4000)*/
    }

    // Function to animate the hint
    // Animate hint text color
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