package ru.ruscalworld.studyplanner.core.model

import java.time.Instant

data class Task(
    val id: Long,
    val name: String,
    val externalName: String?,
    val description: String?,
    val groupId: Long,
    val status: Status,
    val difficulty: Int,
    val deadline: Instant?,
) {
    data class CreateRequest(
        val name: String,
        val externalName: String?,
        val description: String?,
        val difficulty: Int,
    )

    data class UpdateRequest(
        val name: String,
        val externalName: String?,
        val description: String?,
        val status: Status,
        val difficulty: Int,
        val deadline: Instant?,
    )

    data class Group(
        val id: Long,
        val name: String,
    ) {
        data class CreateRequest(
            val name: String,
        )

        data class UpdateRequest(
            val name: String,
        )
    }

    data class Link(
        val id: Long,
        override val name: String,
        override val url: String,
    ) : EntityLink {
        data class CreateRequest(
            val name: String,
            val url: String,
        )

        data class UpdateRequest(
            val name: String,
            val url: String,
        )
    }

    enum class Status {
        NotPublished, Available,
    }
}
