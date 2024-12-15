package ru.ruscalworld.studyplanner.forms.task.create

import ru.ruscalworld.studyplanner.core.model.Task

data class CreateTaskState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val task: Task? = null,
)
