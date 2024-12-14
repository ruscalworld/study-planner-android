package ru.ruscalworld.studyplanner.forms.curriculum.create

import ru.ruscalworld.studyplanner.core.model.Curriculum

data class CreateCurriculumState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val curriculum: Curriculum? = null,
)
