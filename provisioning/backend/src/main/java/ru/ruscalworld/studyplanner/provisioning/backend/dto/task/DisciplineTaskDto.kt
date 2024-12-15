package ru.ruscalworld.studyplanner.provisioning.backend.dto.task

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.DisciplineTask
import ru.ruscalworld.studyplanner.core.model.Task.Status
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO
import ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline.DisciplineDto
import java.time.Instant

@Serializable
data class DisciplineTaskDto(
    val id: Long,
    val name: String,
    val externalName: String?,
    val description: String?,
    val groupId: Long,
    val status: Status,
    val difficulty: Int,
    val deadline: String?,
    val discipline: DisciplineDto,
): DTO<DisciplineTask> {
    override fun toInternalObject() = DisciplineTask(
        id, name, externalName, description, groupId, status, difficulty,
        deadline?.let { Instant.parse(it) }, discipline.toInternalObject(),
    )
}
