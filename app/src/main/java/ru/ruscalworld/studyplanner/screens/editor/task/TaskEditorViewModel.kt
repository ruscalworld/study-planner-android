package ru.ruscalworld.studyplanner.screens.editor.task

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.repository.TaskLinkRepository
import ru.ruscalworld.studyplanner.core.repository.TaskRepository
import ru.ruscalworld.studyplanner.forms.link.create.CreateLinkRequest
import ru.ruscalworld.studyplanner.ui.exceptions.VisibleException
import java.time.Instant
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class TaskEditorViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskLinkRepository: TaskLinkRepository,
) : ViewModel() {
    companion object {
        const val TAG: String = "TaskEditorViewModel"
    }

    val uiState = MutableStateFlow(TaskEditorState())

    val name = MutableStateFlow(TextFieldValue())
    val externalName = MutableStateFlow(TextFieldValue())
    val description = MutableStateFlow(TextFieldValue())
    val difficulty = MutableStateFlow(TextFieldValue())
    val deadline: MutableStateFlow<Long?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            combine(
                name, externalName, description, difficulty, deadline
            ) { name, externalName, description, difficulty, deadline -> Task.UpdateRequest(
                name.text,
                externalName.text.ifBlank { null },
                description.text.ifBlank { null },
                Task.Status.Available,
                difficulty.text.ifBlank { "1" }.toInt(),
                deadline?.let { Instant.ofEpochMilli(it) },
            ) }
                .debounce(1000)
                .distinctUntilChanged()
                .collect { updateTask(it) }
        }
    }

    fun load(disciplineId: Long, taskId: Long) {
        uiState.value = TaskEditorState(isLoading = true, disciplineId = disciplineId)

        viewModelScope.launch {
            try {
                val taskFetcher = async { taskRepository.getTask(disciplineId, taskId) }
                val linksFetcher = async { taskLinkRepository.getLinks(disciplineId, taskId) }

                val task = taskFetcher.await()

                name.value = TextFieldValue(task.name)
                externalName.value = TextFieldValue(task.externalName ?: "")
                description.value = TextFieldValue(task.description ?: "")
                difficulty.value = TextFieldValue(task.difficulty.toString())
                deadline.value = task.deadline?.toEpochMilli()

                uiState.value = TaskEditorState(
                    isLoading = false,

                    disciplineId = disciplineId,
                    task = task,
                    links = linksFetcher.await(),
                )
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

    suspend fun createLink(disciplineId: Long, request: CreateLinkRequest): Task.Link {
        val task = uiState.value.task

        return if (task == null)
            throw VisibleException(R.string.editor_task_error_no_task)
        else
            taskLinkRepository.createLink(disciplineId, task.id, Task.Link.CreateRequest(request.name, request.url))
    }

    private fun updateTask(request: Task.UpdateRequest) {
        val task = uiState.value.task ?: return
        val disciplineId = uiState.value.disciplineId ?: return

        viewModelScope.launch {
            Log.d(TAG, "updateTask: ${task.id}, \"${name.value.text}\"")

            try {
                if (!difficulty.value.text.isDigitsOnly()) return@launch

                taskRepository.updateTask(disciplineId, task.id, request)
            } catch (e: Exception) {
                Log.e(TAG, "Data update failed", e)
                uiState.update { it.copy(error = e) }
            }
        }
    }

    fun deleteTask(disciplineId: Long, taskId: Long, then: () -> Unit) {
        viewModelScope.launch {
            Log.d(TAG, "deleteTask: $disciplineId/$taskId")

            try {
                taskRepository.deleteTask(disciplineId, taskId)
                then()
            } catch (e: Exception) {
                Log.e(TAG, "Deletion failed", e)
                uiState.update { it.copy(error = e) }
            }
        }
    }

    fun onNameChanged(value: TextFieldValue) {
        name.value = value
    }

    fun onExternalNameChanged(value: TextFieldValue) {
        externalName.value = value
    }

    fun onDescriptionChanged(value: TextFieldValue) {
        description.value = value
    }

    fun onDifficultyChanged(value: TextFieldValue) {
        if (!value.text.isDigitsOnly()) return
        difficulty.value = value
    }

    fun onDeadlineChanged(value: Long?) {
        deadline.value = value
    }

    fun onLinkCreated(link: Task.Link) {
        uiState.update { it.copy(links = it.links!! + link) }
    }
}
