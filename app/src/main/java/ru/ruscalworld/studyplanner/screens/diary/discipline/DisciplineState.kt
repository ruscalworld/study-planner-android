package ru.ruscalworld.studyplanner.screens.diary.discipline

import ru.ruscalworld.studyplanner.core.model.Discipline

data class DisciplineState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val discipline: Discipline? = null,
)
