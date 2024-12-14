package ru.ruscalworld.studyplanner.forms.curriculum.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.Curriculum
import javax.inject.Inject

@HiltViewModel
class CreateCurriculumModalViewModel @Inject constructor() : ViewModel() {
    val uiState = MutableStateFlow(CreateCurriculumState())

    fun createCurriculum(request: CreateCurriculumRequest) {
        uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            delay(1000)
            uiState.update { it.copy(isLoading = false, error = null, curriculum = Curriculum(1, "Test")) }
        }
    }
}
