package ru.ruscalworld.studyplanner.provisioning.backend.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignInParams(
    val idToken: String,
)
