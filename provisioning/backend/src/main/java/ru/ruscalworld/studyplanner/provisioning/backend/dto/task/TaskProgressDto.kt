package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO
import java.time.Instant

@Serializable
data class TaskProgressDto(
    val status: TaskProgress.Status,
    val startedAt: String?,
    val completedAt: String?,
) : DTO<TaskProgress> {
    constructor(progress: TaskProgress): this(progress.status, null, null)

    override fun toInternalObject() = TaskProgress(
        status,
        startedAt?.let { Instant.parse(it) },
        completedAt?.let { Instant.parse(it) },
    )
}
