package ru.ruscalworld.studyplanner.core.auth

interface AuthenticationManager {
    suspend fun authenticate(token: String)
    fun isAuthenticated(): Boolean
}
