package ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Discipline

@Serializable
data class CreateDisciplineLinkRequestDto(
    val name: String,
    val url: String
) {
    constructor(request: Discipline.Link.CreateRequest): this(request.name, request.url)
}
