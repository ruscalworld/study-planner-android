package ru.ruscalworld.studyplanner.screens.welcome.curriculum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.ui.exceptions.VisibleException
import javax.inject.Inject

@HiltViewModel
class PickCurriculumViewModel @Inject constructor() : ViewModel() {
    companion object {
        private const val TAG = "PickCurriculumViewModel"
    }

    val uiState = MutableStateFlow(PickCurriculumState())

    fun addExistingCurriculum(request: AddExistingCurriculumRequest) {
        uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            delay(1000)
            uiState.update { it.copy(isLoading = false, error = VisibleException(R.string.auth_error_invalid_credential)) }
        }
    }
}
