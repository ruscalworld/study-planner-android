package ru.ruscalworld.studyplanner.provisioning.backend

import ru.ruscalworld.studyplanner.provisioning.backend.dto.auth.Credentials

interface CredentialsSupplier {
    suspend fun loadCredentials(): Credentials?
    suspend fun storeCredentials(credentials: Credentials)
    suspend fun clearCredentials()
}
