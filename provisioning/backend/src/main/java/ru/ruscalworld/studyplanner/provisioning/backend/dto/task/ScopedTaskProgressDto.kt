package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.ScopedTaskProgress
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO

@Serializable
data class ScopedTaskProgressDto(
    val taskId: Long,
    val taskGroupId: Long,
    val status: TaskProgress.Status,
    val startedAt: String?,
    val completedAt: String?,
): DTO<ScopedTaskProgress> {
    override fun toInternalObject() = ScopedTaskProgress(
        taskId, taskGroupId, TaskProgressDto(status, startedAt, completedAt).toInternalObject(),
    )
}
