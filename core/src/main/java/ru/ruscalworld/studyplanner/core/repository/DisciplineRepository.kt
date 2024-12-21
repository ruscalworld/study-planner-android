package ru.ruscalworld.studyplanner.core.repository

import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.GenericStats
import ru.ruscalworld.studyplanner.core.model.ScopedTaskProgress

interface DisciplineRepository {
    suspend fun getDisciplines(curriculumId: Long): List<Discipline>
    suspend fun getDiscipline(curriculumId: Long, id: Long): Discipline
    suspend fun createDiscipline(curriculumId: Long, request: Discipline.CreateRequest): Discipline
    suspend fun updateDiscipline(curriculumId: Long, id: Long, request: Discipline.UpdateRequest): Discipline
    suspend fun deleteDiscipline(curriculumId: Long, id: Long)

    suspend fun getStats(id: Long): GenericStats
    suspend fun getProgress(id: Long): List<ScopedTaskProgress>
}
