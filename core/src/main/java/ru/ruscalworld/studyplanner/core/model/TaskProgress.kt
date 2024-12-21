package ru.ruscalworld.studyplanner.core.model

import java.time.Instant

data class TaskProgress(
    val status: Status,
    val startedAt: Instant? = null,
    val completedAt: Instant? = null,
) {
    enum class Status {
        NotStarted, InProgress, NeedsProtection, Completed,
    }
}
