package com.palash.luckylottery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.palash.luckylottery.R
import com.palash.luckylottery.databinding.FragmentLiveGameBinding
import com.palash.luckylottery.databinding.FragmentTestingBinding
import com.palash.luckylottery.models.TaskItem
import com.palash.luckylottery.view_models.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestingFragment : Fragment() {
    private var _binding : FragmentTestingBinding?=null
    private val binding get() = _binding!!

    private lateinit var viewModel: TaskViewModel
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTestingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        textView = binding.textView

        val taskList = listOf(
            TaskItem(1, "Task 1"),
            TaskItem(2, "Task 2"),
            TaskItem(3, "Task 3")
        )

        // Start executing tasks sequentially
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.executeTasksSequentially(taskList) { result ->
                updateUI(result)
            }
        }
    }

    private fun updateUI(result: String) {
        textView.append("$result\n") // Append result to TextView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}