package ru.ruscalworld.studyplanner.forms.task.group.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.repository.TaskGroupRepository
import javax.inject.Inject

@HiltViewModel
class CreateTaskGroupModalViewModel @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository,
) : ViewModel() {
    companion object {
        const val TAG = "CreateTaskGroupModalViewModel"
    }

    val uiState = MutableStateFlow(CreateTaskGroupState())

    fun createTaskGroup(disciplineId: Long, request: Task.Group.CreateRequest) {
        uiState.update { CreateTaskGroupState(isLoading = true) }

        viewModelScope.launch {
            try {
                val taskGroup = taskGroupRepository.createGroup(disciplineId, request)
                uiState.update { it.copy(isLoading = false, error = null, taskGroup = taskGroup) }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to create task group", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }
}
