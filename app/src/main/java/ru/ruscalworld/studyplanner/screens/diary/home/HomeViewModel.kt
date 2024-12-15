package ru.ruscalworld.studyplanner.screens.diary.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.repository.CurriculumRepository
import ru.ruscalworld.studyplanner.core.repository.DisciplineRepository
import ru.ruscalworld.studyplanner.settings.ActiveCurriculumStore
import ru.ruscalworld.studyplanner.ui.exceptions.VisibleException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val activeCurriculumStore: ActiveCurriculumStore,
    private val curriculumRepository: CurriculumRepository,
    private val disciplineRepository: DisciplineRepository,
) : ViewModel() {
    companion object {
        const val TAG = "HomeViewModel"
    }

    val uiState = MutableStateFlow(HomeState())

    fun load() {
        uiState.value = HomeState(isLoading = true)

        viewModelScope.launch {
            try {
                val curriculumId = activeCurriculumStore.loadActiveCurriculum()
                    ?: throw VisibleException(R.string.diary_home_error_no_curriculum)

                val upcomingTasksFetcher = async {
                    curriculumRepository.getUpcomingTasks(curriculumId)
                }

                val disciplinesFetcher = async {
                    disciplineRepository.getDisciplines(curriculumId)
                }

                val disciplines = disciplinesFetcher.await()

                val disciplineProgressFetcher = disciplines.map {
                    async { DisciplineProgress(it, disciplineRepository.getState(it.id)) }
                }

                val state = HomeState(
                    isLoading = false,

                    disciplines = disciplineProgressFetcher.awaitAll(),
                    prioritizedTasks = upcomingTasksFetcher.await(),
                )

                uiState.value = state
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }
}
