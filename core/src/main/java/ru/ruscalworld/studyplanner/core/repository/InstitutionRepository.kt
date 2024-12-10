package ru.ruscalworld.studyplanner.core.repository

import ru.ruscalworld.studyplanner.core.model.Institution

interface InstitutionRepository {
    suspend fun getInstitutions(): List<Institution>
    suspend fun getInstitution(): Institution
}
