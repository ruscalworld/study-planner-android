package ru.ruscalworld.studyplanner.screens.diary.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.screens.editor.discipline.DisciplineEditorViewModel.Companion.TAG
import ru.ruscalworld.studyplanner.settings.ActiveCurriculumStore
import ru.ruscalworld.studyplanner.ui.exceptions.VisibleException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val activeCurriculumStore: ActiveCurriculumStore,
) : ViewModel() {
    val uiState = MutableStateFlow(HomeState())

    fun load() {
        uiState.value = HomeState(isLoading = true)

        viewModelScope.launch {
            try {
                val curriculumId = activeCurriculumStore.loadActiveCurriculum()
                    ?: throw VisibleException(R.string.diary_home_error_no_curriculum)

//                val curriculumFetcher = async { curriculumRepository.getCurriculums() }

                val state = HomeState(
                    isLoading = false,

                    // TODO
                )

                uiState.value = state
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }
}
