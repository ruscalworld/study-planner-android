package ru.ruscalworld.studyplanner.screens.options.info

import ru.ruscalworld.studyplanner.core.model.Curriculum

data class InfoState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val curriculums: List<Curriculum>? = null,
    val signedOut: Boolean = false,
    val activeCurriculumChanged: Boolean = false,
)
