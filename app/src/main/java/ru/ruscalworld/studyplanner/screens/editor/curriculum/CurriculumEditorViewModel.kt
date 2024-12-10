package ru.ruscalworld.studyplanner.screens.editor.curriculum

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CurriculumEditorViewModel @Inject constructor() : ViewModel() {
    val uiState = MutableStateFlow(CurriculumEditorState())
}
