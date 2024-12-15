package ru.ruscalworld.studyplanner.provisioning.backend.dto.common

import kotlinx.serialization.Serializable
import ru.ruscalworld.studyplanner.core.model.GenericStats
import ru.ruscalworld.studyplanner.provisioning.backend.dto.DTO

@Serializable
data class GenericStatsDto(
    val completedTasks: Int,
    val inProgressTasks: Int,
    val goalTasks: Int,
    val availableTasks: Int,
    val totalTasks: Int,
) : DTO<GenericStats> {
    override fun toInternalObject() = GenericStats(
        completedTasks,
        inProgressTasks,
        goalTasks,
        availableTasks,
        totalTasks
    )
}
