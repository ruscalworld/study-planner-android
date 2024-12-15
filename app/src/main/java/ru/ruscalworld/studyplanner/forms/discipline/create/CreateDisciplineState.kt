package ru.ruscalworld.studyplanner.forms.discipline.create

import ru.ruscalworld.studyplanner.core.model.Discipline

data class CreateDisciplineState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val discipline: Discipline? = null,
)
