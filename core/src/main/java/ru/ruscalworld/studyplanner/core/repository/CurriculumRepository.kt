package ru.ruscalworld.studyplanner.core.repository

import ru.ruscalworld.studyplanner.core.model.Curriculum

interface CurriculumRepository {
    suspend fun getCurriculums(): List<Curriculum>
    suspend fun getCurriculum(id: Long): Curriculum
}
