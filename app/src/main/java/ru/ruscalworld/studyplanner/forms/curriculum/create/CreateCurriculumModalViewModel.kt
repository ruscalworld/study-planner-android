package ru.ruscalworld.studyplanner.forms.curriculum.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.core.repository.CurriculumRepository
import ru.ruscalworld.studyplanner.settings.ActiveCurriculumStore
import javax.inject.Inject

@HiltViewModel
class CreateCurriculumModalViewModel @Inject constructor(
    private val curriculumRepository: CurriculumRepository,
    private val activeCurriculumStore: ActiveCurriculumStore,
) : ViewModel() {
    companion object {
        const val TAG = "CreateCurriculumModalViewModel"
    }

    val uiState = MutableStateFlow(CreateCurriculumState())

    fun createCurriculum(request: Curriculum.CreateRequest) {
        uiState.update { CreateCurriculumState(isLoading = true) }

        viewModelScope.launch {
            try {
                val curriculum = curriculumRepository.createCurriculum(request)
                activeCurriculumStore.storeActiveCurriculum(curriculum.id)
                uiState.update { it.copy(isLoading = false, error = null, curriculum = curriculum) }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to create curriculum", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }
}
