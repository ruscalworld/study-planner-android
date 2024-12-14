package ru.ruscalworld.studyplanner.core.repository

import ru.ruscalworld.studyplanner.core.model.Curriculum

interface CurriculumRepository {
    suspend fun getCurriculums(): List<Curriculum>
    suspend fun getCurriculum(id: Long): Curriculum
    suspend fun createCurriculum(request: Curriculum.CreateRequest): Curriculum
    suspend fun updateCurriculum(id: Long, request: Curriculum.UpdateRequest): Curriculum
    suspend fun deleteCurriculum(id: Long)
}
