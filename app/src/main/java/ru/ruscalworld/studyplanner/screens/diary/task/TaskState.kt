package ru.ruscalworld.studyplanner.screens.diary.task

import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.model.TaskProgress

data class TaskState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,

    val disciplineId: Long? = null,
    val task: Task? = null,
    val links: List<Task.Link>? = null,
    val progress: TaskProgress? = null,
)
