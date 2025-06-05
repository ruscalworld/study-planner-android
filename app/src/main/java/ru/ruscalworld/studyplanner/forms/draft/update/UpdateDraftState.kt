package ru.ruscalworld.studyplanner.forms.draft.update

import ru.ruscalworld.studyplanner.core.model.Draft

data class UpdateDraftState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val draft: Draft? = null,
    val isDeleted: Boolean = false,
)
