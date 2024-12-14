package ru.ruscalworld.studyplanner.screens.editor.curriculum

import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CurriculumEditorViewModel @Inject constructor() : ViewModel() {
    val uiState = MutableStateFlow(CurriculumEditorState())

    val name = MutableStateFlow(TextFieldValue())
    val semesterNo = MutableStateFlow(TextFieldValue())

    fun onNameChanged(value: TextFieldValue) {
        name.value = value
    }

    fun onSemesterNoChanged(value: TextFieldValue) {
        if (!value.text.isDigitsOnly()) return
        semesterNo.value = value
    }
}
