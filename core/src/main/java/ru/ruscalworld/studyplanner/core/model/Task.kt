package ru.ruscalworld.studyplanner.core.model

import java.util.Date

data class Task(
    val id: Long,
    val name: String,
    val externalName: String?,
    val description: String?,
    val groupId: Long,
    val status: Status,
    val difficulty: Int,
    val deadline: Date?,
) {
    data class Group(
        val id: Long,
        val name: String,
    )

    data class Link(
        val id: Long,
        override val name: String,
        override val url: String,
    ) : EntityLink

    enum class Status {
        NotPublished, Available,
    }
}
