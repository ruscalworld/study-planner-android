package ru.ruscalworld.studyplanner.forms.discipline.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.repository.DisciplineRepository
import javax.inject.Inject

@HiltViewModel
class CreateDisciplineModalViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository,
) : ViewModel() {
    companion object {
        const val TAG = "CreateDisciplineModalViewModel"
    }

    val uiState = MutableStateFlow(CreateDisciplineState())

    fun createDiscipline(curriculumId: Long, request: Discipline.CreateRequest) {
        uiState.update { CreateDisciplineState(isLoading = true) }

        viewModelScope.launch {
            try {
                val discipline = disciplineRepository.createDiscipline(curriculumId, request)
                uiState.update { it.copy(isLoading = false, error = null, discipline = discipline) }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to create discipline", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }
}
