package ru.ruscalworld.studyplanner.provisioning.backend.dto.draft

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Draft

@Serializable
data class UpdateDraftRequestDto(
    val text: String,
) {
    constructor(request: Draft.UpdateRequest): this(request.text)
}
