package ru.ruscalworld.studyplanner.provisioning.backend.dto.draft

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Draft
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO
import java.time.Instant

@Serializable
data class DraftDto(
    val id: Long,
    val text: String,
    val createdAt: String,
): DTO<Draft> {
    override fun toInternalObject(): Draft {
        return Draft(id, text, Instant.parse(createdAt))
    }
}
