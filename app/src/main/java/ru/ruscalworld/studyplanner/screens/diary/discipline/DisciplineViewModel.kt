package ru.ruscalworld.studyplanner.screens.diary.discipline

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DisciplineViewModel @Inject constructor() : ViewModel() {
    val uiState = MutableStateFlow(DisciplineState())
}
