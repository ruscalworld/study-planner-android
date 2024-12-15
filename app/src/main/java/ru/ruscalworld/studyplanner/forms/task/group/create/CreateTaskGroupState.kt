package ru.ruscalworld.studyplanner.forms.task.group.create

import ru.ruscalworld.studyplanner.core.model.Task

data class CreateTaskGroupState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val taskGroup: Task.Group? = null,
)
