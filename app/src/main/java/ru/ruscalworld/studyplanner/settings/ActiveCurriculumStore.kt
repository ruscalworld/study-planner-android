package ru.ruscalworld.studyplanner.settings

interface ActiveCurriculumStore {
    suspend fun loadActiveCurriculum(): Long?
    suspend fun storeActiveCurriculum(id: Long?)
}
