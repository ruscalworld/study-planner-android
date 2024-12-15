package ru.ruscalworld.studyplanner.core.auth

interface AuthenticationManager {
    suspend fun authenticate(token: String)
    suspend fun signOut()
}
