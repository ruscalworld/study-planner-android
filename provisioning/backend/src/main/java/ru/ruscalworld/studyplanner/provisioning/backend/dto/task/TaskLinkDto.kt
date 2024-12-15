package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO

@Serializable
data class TaskLinkDto(
    val id: Long,
    val name: String,
    val url: String
) : DTO<Task.Link> {
    override fun toInternalObject() = Task.Link(id, name, url)
}
