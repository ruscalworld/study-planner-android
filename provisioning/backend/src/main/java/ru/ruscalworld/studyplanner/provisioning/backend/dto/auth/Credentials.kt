package ru.ruscalworld.studyplanner.provisioning.backend.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val accessToken: String,
    val tokenType: String,
)
