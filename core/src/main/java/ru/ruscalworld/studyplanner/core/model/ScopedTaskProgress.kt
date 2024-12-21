package ru.ruscalworld.studyplanner.core.model

data class ScopedTaskProgress(
    val taskId: Long,
    val taskGroupId: Long,
    val taskProgress: TaskProgress,
)
