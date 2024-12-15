package ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO

@Serializable
data class DisciplineLinkDto(
    val id: Long,
    val name: String,
    val url: String
) : DTO<Discipline.Link> {
    override fun toInternalObject() = Discipline.Link(id, name, url)
}
