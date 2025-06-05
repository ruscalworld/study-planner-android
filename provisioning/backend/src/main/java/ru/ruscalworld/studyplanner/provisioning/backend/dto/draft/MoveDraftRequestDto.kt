package ru.ruscalworld.studyplanner.provisioning.backend.dto.draft

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Draft

@Serializable
data class MoveDraftRequestDto(
    val text: String,
    val disciplineId: Long,
    val taskGroupId: Long,
) {
    constructor(request: Draft.MoveRequest) : this(
        request.text,
        request.disciplineId,
        request.taskGroupId,
    )
}
