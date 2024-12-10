package ru.ruscalworld.studyplanner.screens.editor.discipline

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DisciplineEditorViewModel @Inject constructor() : ViewModel() {
    val uiState = MutableStateFlow(DisciplineEditorState())
}
