package ru.ruscalworld.studyplanner.screens.editor.curriculum

data class CurriculumEditorState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
)
