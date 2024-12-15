package ru.ruscalworld.studyplanner.screens.diary.home

import ru.ruscalworld.studyplanner.core.model.DisciplineTask

data class HomeState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val prioritizedTasks: List<DisciplineTask>? = null,
    val disciplines: List<DisciplineProgress>? = null,
)
