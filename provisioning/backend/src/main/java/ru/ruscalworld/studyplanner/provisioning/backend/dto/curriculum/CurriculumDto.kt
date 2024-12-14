package ru.ruscalworld.studyplanner.provisioning.backend.dto.curriculum

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO

@Serializable
data class CurriculumDto(
    val id: Long,
    val name: String,
    val semester: Int,
    val role: String? = null,
) : DTO<Curriculum> {
    override fun toInternalObject() = Curriculum(id, name, semester)
}
