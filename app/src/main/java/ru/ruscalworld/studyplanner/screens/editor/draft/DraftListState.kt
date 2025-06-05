package ru.ruscalworld.studyplanner.screens.editor.draft

import ru.ruscalworld.studyplanner.core.model.Draft

data class DraftListState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val drafts: List<Draft>? = null,
)
