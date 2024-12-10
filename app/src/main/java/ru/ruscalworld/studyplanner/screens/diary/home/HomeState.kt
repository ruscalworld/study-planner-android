package ru.ruscalworld.studyplanner.screens.diary.home

import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Task

data class HomeState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val prioritizedTasks: List<Task>? = null,
    val disciplines: List<Discipline>? = null,
)
