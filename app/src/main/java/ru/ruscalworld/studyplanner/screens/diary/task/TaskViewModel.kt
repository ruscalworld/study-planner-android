package ru.ruscalworld.studyplanner.screens.diary.task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.core.repository.TaskLinkRepository
import ru.ruscalworld.studyplanner.core.repository.TaskRepository
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskLinkRepository: TaskLinkRepository,
): ViewModel() {
    companion object {
        const val TAG = "TaskViewModel"
    }

    val uiState = MutableStateFlow(TaskState())

    fun load(disciplineId: Long, taskId: Long) {
        uiState.value = TaskState(isLoading = true, disciplineId = disciplineId)

        viewModelScope.launch {
            try {
                val taskFetcher = async { taskRepository.getTask(disciplineId, taskId) }
                val linksFetcher = async { taskLinkRepository.getLinks(disciplineId, taskId) }
                val taskProgressFetcher = async { taskRepository.getTaskProgress(disciplineId, taskId) }

                uiState.value = TaskState(
                    isLoading = false,

                    task = taskFetcher.await(),
                    links = linksFetcher.await(),
                    progress = taskProgressFetcher.await(),
                )
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

    fun updateProgress(disciplineId: Long, taskId: Long, progress: TaskProgress) {
        val oldProgress = uiState.value.progress
        uiState.update { it.copy(progress = progress) }

        viewModelScope.launch {
            try {
                taskRepository.updateTaskProgress(disciplineId, taskId, progress)
            } catch (e: Exception) {
                Log.e(TAG, "Unable to update progress", e)
                uiState.update { it.copy(error = e, progress = oldProgress) }
            }
        }
    }
}
