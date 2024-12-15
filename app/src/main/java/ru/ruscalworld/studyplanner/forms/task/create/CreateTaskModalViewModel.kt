package ru.ruscalworld.studyplanner.forms.task.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.repository.TaskRepository
import javax.inject.Inject

@HiltViewModel
class CreateTaskModalViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {
    companion object {
        const val TAG = "CreateTaskModalViewModel"
    }

    val uiState = MutableStateFlow(CreateTaskState())

    fun createTask(
        disciplineId: Long,
        taskGroupId: Long,
        request: Task.CreateRequest,
        onTaskCreated: (Task) -> Unit,
    ) {
        uiState.update { CreateTaskState(isLoading = true) }

        viewModelScope.launch {
            try {
                val task = taskRepository.createTask(disciplineId, taskGroupId, request)
                onTaskCreated(task)
                uiState.update { it.copy(isLoading = false, error = null, task = task) }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to create task", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }
}
