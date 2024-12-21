package ru.ruscalworld.studyplanner.screens.diary.discipline

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.core.repository.DisciplineLinkRepository
import ru.ruscalworld.studyplanner.core.repository.DisciplineRepository
import ru.ruscalworld.studyplanner.core.repository.TaskGroupRepository
import ru.ruscalworld.studyplanner.core.repository.TaskRepository
import ru.ruscalworld.studyplanner.settings.ActiveCurriculumStore
import ru.ruscalworld.studyplanner.ui.exceptions.VisibleException
import javax.inject.Inject

@HiltViewModel
class DisciplineViewModel @Inject constructor(
    private val activeCurriculumStore: ActiveCurriculumStore,
    private val disciplineRepository: DisciplineRepository,
    private val disciplineLinkRepository: DisciplineLinkRepository,
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,
) : ViewModel() {
    companion object {
        const val TAG = "DisciplineViewModel"
    }

    val uiState = MutableStateFlow(DisciplineState())

    fun load(disciplineId: Long) {
        uiState.value = DisciplineState(isLoading = true)

        viewModelScope.launch {
            try {
                val curriculumId = activeCurriculumStore.loadActiveCurriculum()
                    ?: throw VisibleException(R.string.diary_home_error_no_curriculum)

                val disciplineFetcher = async {
                    disciplineRepository.getDiscipline(curriculumId, disciplineId)
                }

                val taskGroupsFetcher = async { taskGroupRepository.getGroups(disciplineId) }
                val linksFetcher = async { disciplineLinkRepository.getLinks(disciplineId) }
                val tasksFetcher = async { taskRepository.getTasks(disciplineId) }
                val progressFetcher = async { disciplineRepository.getProgress(disciplineId) }

                val mappedTasks = HashMap<Long, HashMap<Long, Pair<Task, TaskProgress?>>>()

                for (t in tasksFetcher.await()) {
                    if (t.groupId !in mappedTasks) mappedTasks[t.groupId] = hashMapOf(t.id to Pair(t, null))
                    else mappedTasks[t.groupId]!![t.id] = Pair(t, null)
                }

                for (p in progressFetcher.await()) {
                    if (p.taskGroupId !in mappedTasks) continue
                    val groupTasks = mappedTasks[p.taskGroupId]!!

                    if (p.taskId !in groupTasks) continue
                    val taskInfo = groupTasks[p.taskId]!!

                    groupTasks[p.taskId] = taskInfo.copy(second = p.taskProgress)
                }

                val tasks = HashMap<Long, List<Pair<Task, TaskProgress>>>()

                for ((groupId, taskMap) in mappedTasks) {
                    tasks[groupId] = taskMap.values.map {
                        Pair(it.first, it.second ?: TaskProgress(TaskProgress.Status.NotStarted))
                    }
                }

                val discipline = disciplineFetcher.await()

                val state = DisciplineState(
                    isLoading = false,

                    discipline = discipline,
                    taskGroups = taskGroupsFetcher.await(),
                    tasks = tasks,
                    links = linksFetcher.await(),
                )

                uiState.value = state
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }
}
