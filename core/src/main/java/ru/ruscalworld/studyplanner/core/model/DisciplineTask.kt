package ru.ruscalworld.studyplanner.core.model

import ru.ruscalworld.studyplanner.core.model.Task.Status
import java.time.Instant

data class DisciplineTask(
    val id: Long,
    val name: String,
    val externalName: String?,
    val description: String?,
    val groupId: Long,
    val status: Status,
    val difficulty: Int,
    val deadline: Instant?,
    val discipline: Discipline
)
