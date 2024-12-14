package ru.ruscalworld.studyplanner.screens.editor.discipline

import ru.ruscalworld.studyplanner.core.model.Task

data class GroupTasks (
    val taskGroupId: Long,
    val tasks: List<Task>,
)
