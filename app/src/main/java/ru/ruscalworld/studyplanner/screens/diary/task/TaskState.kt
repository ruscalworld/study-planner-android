package ru.ruscalworld.studyplanner.screens.diary.task

import ru.ruscalworld.studyplanner.core.model.Task

data class TaskState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val task: Task? = null,
)
