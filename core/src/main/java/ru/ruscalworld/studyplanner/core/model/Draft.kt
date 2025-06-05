package ru.ruscalworld.studyplanner.core.model

import java.time.Instant

data class Draft(
    val id: Long,
    val text: String,
    val createdAt: Instant,
) {
    data class CreateRequest(
        val text: String,
    )

    data class UpdateRequest(
        val text: String,
    )

    data class MoveRequest(
        val text: String,
        val taskGroupId: Long,
        val disciplineId: Long,
    )
}
