package ru.ruscalworld.studyplanner.provisioning.backend.dto.curriculum

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Curriculum

@Serializable
data class CreateCurriculumRequestDto(
    val name: String,
    val semester: Int,
) {
    constructor(request: Curriculum.CreateRequest) : this(request.name, request.semester)
}
