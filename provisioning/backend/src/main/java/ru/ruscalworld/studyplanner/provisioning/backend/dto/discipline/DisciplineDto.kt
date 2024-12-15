package ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO

@Serializable
data class DisciplineDto(
    val id: Long,
    val name: String,
): DTO<Discipline> {
    override fun toInternalObject() = Discipline(id, name)
}
