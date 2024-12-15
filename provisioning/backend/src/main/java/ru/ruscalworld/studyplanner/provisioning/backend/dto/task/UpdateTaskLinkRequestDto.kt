package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Task

@Serializable
data class UpdateTaskLinkRequestDto(
    val name: String,
    val url: String
) {
    constructor(request: Task.Link.UpdateRequest): this(request.name, request.url)
}
