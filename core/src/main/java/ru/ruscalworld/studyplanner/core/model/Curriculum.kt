package ru.ruscalworld.studyplanner.core.model

data class Curriculum(
    val id: Long,
    val name: String,
    val semester: Int,
) {
    data class CreateRequest(
        val name: String,
        val semester: Int,
    )

    data class UpdateRequest(
        val name: String,
        val semester: Int,
    )
}
