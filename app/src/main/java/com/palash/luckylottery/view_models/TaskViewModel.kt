package com.palash.luckylottery.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palash.luckylottery.models.TaskItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor() : ViewModel() {

    // Function to simulate a task
    private suspend fun performTask(item: TaskItem): String {
        return withContext(Dispatchers.IO) { // Simulate a background task
            delay(1000) // Simulate a delay (e.g., network request)
            "Task ${item.name} completed"
        }
    }

    // Function to execute tasks sequentially
    suspend fun executeTasksSequentially(taskList: List<TaskItem>, onTaskCompleted: (String) -> Unit) {
        for (task in taskList) {
            val result = performTask(task)
            onTaskCompleted(result) // Notify UI of task completion
        }
        onTaskCompleted("All tasks completed!")
    }
}