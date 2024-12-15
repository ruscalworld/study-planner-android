package ru.ruscalworld.studyplanner.core.repository

import ru.ruscalworld.studyplanner.core.model.Discipline

interface DisciplineLinkRepository {
    suspend fun getLinks(disciplineId: Long): List<Discipline.Link>
    suspend fun createLink(disciplineId: Long, request: Discipline.Link.CreateRequest): Discipline.Link
    suspend fun updateLink(disciplineId: Long, id: Long, request: Discipline.Link.UpdateRequest): Discipline.Link
    suspend fun deleteLink(disciplineId: Long, id: Long)
}