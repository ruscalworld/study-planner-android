package ru.ruscalworld.studyplanner.screens.editor.discipline

data class DisciplineEditorState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
)
