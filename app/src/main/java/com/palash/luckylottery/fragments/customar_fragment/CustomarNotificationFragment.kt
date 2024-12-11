package com.palash.luckylottery.fragments.customar_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.palash.luckylottery.R
import com.palash.luckylottery.databinding.FragmentCustomarNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomarNotificationFragment : Fragment() {
    private var _binding: FragmentCustomarNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustomarNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.getString("data") ?: "No Data"
        Toast.makeText(requireContext(), args, Toast.LENGTH_SHORT).show()
        //view.findViewById<TextView>(R.id.textViewData)?.text = args
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}