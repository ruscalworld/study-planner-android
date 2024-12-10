package ru.ruscalworld.studyplanner.screens.editor.task

data class TaskEditorState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
)
