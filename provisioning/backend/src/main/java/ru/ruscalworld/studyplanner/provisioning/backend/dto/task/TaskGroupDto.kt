package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO

@Serializable
data class TaskGroupDto(
    val id: Long,
    val name: String,
) : DTO<Task.Group> {
    override fun toInternalObject() = Task.Group(id, name)
}
