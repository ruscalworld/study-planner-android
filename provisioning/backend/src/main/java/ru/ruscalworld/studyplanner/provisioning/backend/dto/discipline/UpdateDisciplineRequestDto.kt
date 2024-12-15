package ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Discipline

@Serializable
data class UpdateDisciplineRequestDto(
    val name: String
) {
    constructor(request: Discipline.UpdateRequest) : this(request.name)
}
