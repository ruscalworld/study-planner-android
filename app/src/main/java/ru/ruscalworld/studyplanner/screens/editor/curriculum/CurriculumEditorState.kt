package ru.ruscalworld.studyplanner.screens.editor.curriculum

import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Draft

data class CurriculumEditorState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val curriculum: Curriculum? = null,
    val disciplines: List<Discipline>? = null,
    val drafts: List<Draft>? = null,
)
