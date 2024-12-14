package ru.ruscalworld.studyplanner.core.model

data class Discipline(
    val id: Long,
    val name: String,
) {
    data class Link(
        val id: Long,
        override val name: String,
        override val url: String
    ) : EntityLink
}
