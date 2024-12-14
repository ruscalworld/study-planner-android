package ru.ruscalworld.studyplanner.core.model

data class TaskProgress(
    val status: Status,
) {
    enum class Status {
        NotStarted, InProgress, NeedsProtection, Completed,
    }
}
