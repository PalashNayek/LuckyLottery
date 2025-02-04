package com.palash.luckylottery.fragments.customar_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.palash.luckylottery.R
import com.palash.luckylottery.databinding.FragmentCustomarHomeBinding
import com.palash.luckylottery.databinding.FragmentCustomarTricketOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomarTricketOrderFragment : Fragment() {
    private var _binding: FragmentCustomarTricketOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomarTricketOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}