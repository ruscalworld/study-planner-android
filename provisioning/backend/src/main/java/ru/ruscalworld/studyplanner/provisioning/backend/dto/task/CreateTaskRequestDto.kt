package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Task

@Serializable
data class CreateTaskRequestDto(
    val name: String,
    val externalName: String?,
    val description: String?,
    val difficulty: Int,
) {
    constructor(request: Task.CreateRequest)
            : this(request.name, request.externalName, request.description, request.difficulty)
}
