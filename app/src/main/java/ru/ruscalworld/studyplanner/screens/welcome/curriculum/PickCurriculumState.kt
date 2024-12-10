package ru.ruscalworld.studyplanner.screens.welcome.curriculum

import ru.ruscalworld.studyplanner.core.model.Curriculum

data class PickCurriculumState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val curriculum: Curriculum? = null,
)
