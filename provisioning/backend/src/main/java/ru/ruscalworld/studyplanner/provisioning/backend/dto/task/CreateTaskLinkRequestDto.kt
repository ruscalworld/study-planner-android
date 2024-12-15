package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Task

@Serializable
data class CreateTaskLinkRequestDto(
    val name: String,
    val url: String
) {
    constructor(request: Task.Link.CreateRequest): this(request.name, request.url)
}
