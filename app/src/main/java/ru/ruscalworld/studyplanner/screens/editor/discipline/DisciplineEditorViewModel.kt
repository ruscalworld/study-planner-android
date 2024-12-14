package ru.ruscalworld.studyplanner.screens.editor.discipline

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Task
import java.util.Date
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class DisciplineEditorViewModel @Inject constructor() : ViewModel() {
    companion object {
        const val TAG: String = "DisciplineEditorViewModel"
    }

    val uiState = MutableStateFlow(DisciplineEditorState())
    val name = MutableStateFlow(TextFieldValue())

    fun load() {
        uiState.value = DisciplineEditorState(isLoading = true)

        viewModelScope.launch {
            try {
                val disciplineFetcher = async {
                    delay(1000)
                    Discipline(1, "Test")
                }

                val taskGroupsFetcher = async {
                    delay(1200)
                    listOf(Task.Group(1, "First Group"), Task.Group(2, "Second Group"))
                }

                val linksFetcher = async {
                    delay(7000)
                    listOf(Discipline.Link(1, "Yandex", "https://yandex.ru"))
                }

                val taskGroups = taskGroupsFetcher.await()

                val tasks = taskGroups.map { taskGroup ->
                    async {
                        delay(500 * taskGroup.id)

                        if (taskGroup.id == 1L)
                            return@async GroupTasks(
                                taskGroupId = 1,
                                tasks = listOf(
                                    Task(
                                        1,
                                        "Example very long task name bla bla bla",
                                        "Sample description",
                                        null,
                                        1,
                                        Task.Status.Available,
                                        1,
                                        Date(System.currentTimeMillis() + 10 * 86400 * 1000)
                                    ),
                                )
                            )

                        if (taskGroup.id == 2L)
                            return@async GroupTasks(
                                taskGroupId = 2,
                                tasks = listOf(
                                    Task(
                                        1,
                                        "Example very long task name bla bla bla",
                                        "Sample description",
                                        null,
                                        2,
                                        Task.Status.Available,
                                        1,
                                        Date(System.currentTimeMillis() + 10 * 86400 * 1000)
                                    ),
                                    Task(
                                        1,
                                        "Example very long task name bla bla bla",
                                        "Sample description",
                                        null,
                                        2,
                                        Task.Status.Available,
                                        1,
                                        Date(System.currentTimeMillis() + 10 * 86400 * 1000)
                                    ),
                                    Task(
                                        1,
                                        "Example very long task name bla bla bla",
                                        "Sample description",
                                        null,
                                        2,
                                        Task.Status.Available,
                                        1,
                                        Date(System.currentTimeMillis() + 10 * 86400 * 1000)
                                    ),
                                )
                            )

                        throw Exception("WTF")
                    }
                }

                val mappedTasks = HashMap<Long, List<Task>>()

                for (g in tasks.awaitAll()) {
                    mappedTasks[g.taskGroupId] = g.tasks
                }

                val discipline = disciplineFetcher.await()

                val state = DisciplineEditorState(
                    isLoading = false,

                    discipline = discipline,
                    taskGroups = taskGroups,
                    tasks = mappedTasks,
                    links = linksFetcher.await(),
                )

                uiState.value = state
                name.value = TextFieldValue(discipline.name)
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

    fun onNameChanged(value: TextFieldValue) {
        name.value = value
    }

    fun onLinkCreated(link: Discipline.Link) {
        uiState.update { it.copy(links = it.links!! + link) }
    }
}
