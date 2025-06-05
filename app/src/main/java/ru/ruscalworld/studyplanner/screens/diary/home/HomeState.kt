package ru.ruscalworld.studyplanner.screens.diary.home

import ru.ruscalworld.studyplanner.core.model.DisciplineTask
import ru.ruscalworld.studyplanner.core.model.Draft
import ru.ruscalworld.studyplanner.core.model.TaskProgress

data class HomeState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val prioritizedTasks: List<Pair<DisciplineTask, TaskProgress>>? = null,
    val disciplines: List<DisciplineProgress>? = null,
    val drafts: List<Draft>? = null,
)
