package ru.ruscalworld.studyplanner.screens.editor.task

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.screens.editor.discipline.DisciplineEditorState
import ru.ruscalworld.studyplanner.screens.editor.discipline.DisciplineEditorViewModel.Companion.TAG
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskEditorViewModel @Inject constructor() : ViewModel() {
    companion object {
        const val TAG: String = "TaskEditorViewModel"
    }

    val uiState = MutableStateFlow(TaskEditorState())

    val name = MutableStateFlow(TextFieldValue())
    val externalName = MutableStateFlow(TextFieldValue())
    val description = MutableStateFlow(TextFieldValue())
    val difficulty = MutableStateFlow(TextFieldValue())
    private val deadline: MutableStateFlow<Long?> = MutableStateFlow(null)

    fun load(disciplineId: Long, taskId: Long) {
        uiState.value = TaskEditorState(isLoading = true)

        viewModelScope.launch {
            try {
                val taskFetcher = async {
                    delay(500)

                    Task(
                        1,
                        "Example very long task name bla bla bla",
                        "Sample description",
                        null,
                        1,
                        Task.Status.Available,
                        1,
                        Date(System.currentTimeMillis() + 10 * 86400 * 1000)
                    )
                }

                val linksFetcher = async {
                    delay(400)

                    listOf(
                        Task.Link(1, "Google", "https://google.com")
                    )
                }

                val task = taskFetcher.await()

                name.value = TextFieldValue(task.name)
                externalName.value = TextFieldValue(task.externalName ?: "")
                description.value = TextFieldValue(task.description ?: "")
                difficulty.value = TextFieldValue(text = task.difficulty.toString())
                deadline.value = task.deadline?.time

                uiState.value = TaskEditorState(
                    isLoading = false,

                    task = task,
                    links = linksFetcher.await(),
                )
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
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
