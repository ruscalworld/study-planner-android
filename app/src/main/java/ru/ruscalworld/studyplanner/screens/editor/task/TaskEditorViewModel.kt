package ru.ruscalworld.studyplanner.screens.editor.task

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TaskEditorViewModel @Inject constructor() : ViewModel() {
    val uiState = MutableStateFlow(TaskEditorState())
}
