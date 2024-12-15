package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.model.Task.Status
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO
import java.time.Instant

@Serializable
data class TaskDto(
    val id: Long,
    val name: String,
    val externalName: String? = null,
    val description: String? = null,
    val groupId: Long,
    val status: Status,
    val difficulty: Int = 1,
    val deadline: String? = null,
) : DTO<Task> {
    override fun toInternalObject() = Task(
        id, name, externalName, description, groupId, status, difficulty,
        deadline?.let { Instant.parse(it) }
    )
}
