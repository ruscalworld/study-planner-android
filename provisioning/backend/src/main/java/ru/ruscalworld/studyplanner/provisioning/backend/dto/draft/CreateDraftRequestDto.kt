package ru.ruscalworld.studyplanner.provisioning.backend.dto.draft

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Draft

@Serializable
data class CreateDraftRequestDto(
    val text: String,
) {
    constructor(request: Draft.CreateRequest): this(request.text)
}
