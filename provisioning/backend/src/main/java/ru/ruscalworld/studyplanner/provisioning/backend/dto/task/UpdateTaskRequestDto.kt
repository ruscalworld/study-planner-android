package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.model.Task.Status
import java.time.format.DateTimeFormatter

@Serializable
data class UpdateTaskRequestDto(
    val name: String,
    val externalName: String?,
    val description: String?,
    val status: Status,
    val difficulty: Int,
    val deadline: String?,
) {
    constructor(request: Task.UpdateRequest) : this(
        request.name,
        request.externalName,
        request.description,
        request.status,
        request.difficulty,
        request.deadline?.let { DateTimeFormatter.ISO_INSTANT.format(it) }
    )
}
