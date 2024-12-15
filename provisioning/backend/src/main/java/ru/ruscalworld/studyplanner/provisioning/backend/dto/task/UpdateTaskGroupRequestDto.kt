package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Task

@Serializable
data class UpdateTaskGroupRequestDto(
    val name: String,
) {
    constructor(request: Task.Group.UpdateRequest): this(request.name)
}
