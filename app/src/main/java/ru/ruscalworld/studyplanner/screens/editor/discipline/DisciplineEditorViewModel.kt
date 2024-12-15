package ru.ruscalworld.studyplanner.screens.editor.discipline

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.repository.DisciplineLinkRepository
import ru.ruscalworld.studyplanner.core.repository.DisciplineRepository
import ru.ruscalworld.studyplanner.core.repository.TaskGroupRepository
import ru.ruscalworld.studyplanner.core.repository.TaskRepository
import ru.ruscalworld.studyplanner.forms.link.create.CreateLinkRequest
import ru.ruscalworld.studyplanner.settings.ActiveCurriculumStore
import ru.ruscalworld.studyplanner.ui.exceptions.VisibleException
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class DisciplineEditorViewModel @Inject constructor(
    private val activeCurriculumStore: ActiveCurriculumStore,
    private val disciplineRepository: DisciplineRepository,
    private val disciplineLinkRepository: DisciplineLinkRepository,
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,
) : ViewModel() {
    companion object {
        const val TAG: String = "DisciplineEditorViewModel"
    }

    val uiState = MutableStateFlow(DisciplineEditorState())
    val name = MutableStateFlow(TextFieldValue())

    init {
        viewModelScope.launch {
            name
                .debounce(1000)
                .distinctUntilChanged()
                .collect { updateDiscipline() }
        }
    }

    fun load(disciplineId: Long) {
        uiState.value = DisciplineEditorState(isLoading = true)

        viewModelScope.launch {
            try {
                val curriculumId = activeCurriculumStore.loadActiveCurriculum()
                    ?: throw VisibleException(R.string.editor_discipline_error_no_discipline)

                val disciplineFetcher = async {
                    disciplineRepository.getDiscipline(curriculumId, disciplineId)
                }

                val taskGroupsFetcher = async { taskGroupRepository.getGroups(disciplineId) }
                val linksFetcher = async { disciplineLinkRepository.getLinks(disciplineId) }
                val tasks = async { taskRepository.getTasks(disciplineId) }

                val mappedTasks = HashMap<Long, List<Task>>()

                for (t in tasks.await()) {
                    if (t.groupId !in mappedTasks) mappedTasks[t.groupId] = mutableListOf(t)
                    else mappedTasks[t.groupId] = mappedTasks[t.groupId]!! + t
                }

                val discipline = disciplineFetcher.await()

                val state = DisciplineEditorState(
                    isLoading = false,

                    curriculumId = curriculumId,
                    discipline = discipline,
                    taskGroups = taskGroupsFetcher.await(),
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

    suspend fun createLink(request: CreateLinkRequest): Discipline.Link {
        val discipline = uiState.value.discipline

        return if (discipline == null)
            throw VisibleException(R.string.editor_discipline_error_no_discipline)
        else
            disciplineLinkRepository.createLink(discipline.id, Discipline.Link.CreateRequest(request.name, request.url))
    }

    private fun updateDiscipline() {
        viewModelScope.launch {
            val discipline = uiState.value.discipline
            val curriculumId = uiState.value.curriculumId
            Log.d(TAG, "updateDiscipline: ${discipline?.id}, \"${name.value.text}\"")
            if (discipline == null || curriculumId == null) return@launch

            try {
                disciplineRepository.updateDiscipline(
                    curriculumId,
                    discipline.id,
                    Discipline.UpdateRequest(name.value.text)
                )
            } catch (e: Exception) {
                Log.e(TAG, "Data update failed", e)
                uiState.update { it.copy(error = e) }
            }
        }
    }

    fun onNameChanged(value: TextFieldValue) {
        name.value = value
    }

    fun onLinkCreated(link: Discipline.Link) {
        uiState.update { it.copy(links = it.links?.plus(link)) }
    }

    fun onTaskGroupCreated(taskGroup: Task.Group) {
        uiState.update {
            it.copy(taskGroups = it.taskGroups?.plus(taskGroup))
        }
    }

    fun onTaskCreated(task: Task) {
        Log.d(TAG, "onTaskCreated invoked")
        uiState.update { state ->
            val currentTasks = state.tasks ?: HashMap()
            val updatedTasks = currentTasks.toMutableMap()

            val groupTasks = updatedTasks[task.groupId]?.toMutableList() ?: mutableListOf()
            groupTasks.add(task)
            updatedTasks[task.groupId] = groupTasks

            state.copy(tasks = HashMap(updatedTasks))
        }
    }
}
