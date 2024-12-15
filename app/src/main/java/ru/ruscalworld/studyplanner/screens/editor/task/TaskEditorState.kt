package ru.ruscalworld.studyplanner.screens.editor.task

import ru.ruscalworld.studyplanner.core.model.Task

data class TaskEditorState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,

    val disciplineId: Long? = null,
    val task: Task? = null,
    val links: List<Task.Link>? = null,
)
