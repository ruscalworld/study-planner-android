package ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Discipline

@Serializable
data class UpdateDisciplineLinkRequestDto(
    val name: String,
    val url: String
) {
    constructor(request: Discipline.Link.UpdateRequest): this(request.name, request.url)
}
