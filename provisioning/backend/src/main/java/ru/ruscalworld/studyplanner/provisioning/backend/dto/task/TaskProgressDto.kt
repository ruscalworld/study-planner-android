package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO

@Serializable
data class TaskProgressDto(
    val status: TaskProgress.Status,
) : DTO<TaskProgress> {
    constructor(progress: TaskProgress): this(progress.status)

    override fun toInternalObject() = TaskProgress(status)
}
