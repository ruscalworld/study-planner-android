package ru.ruscalworld.studyplanner.screens.editor.curriculum

import ru.ruscalworld.studyplanner.core.model.Discipline

data class CurriculumEditorState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val disciplines: List<Discipline>? = null,
)
