package ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Discipline

@Serializable
data class CreateDisciplineRequestDto(
    val name: String
) {
    constructor(request: Discipline.CreateRequest) : this(request.name)
}
