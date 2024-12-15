package ru.ruscalworld.studyplanner.core.repository

import ru.ruscalworld.studyplanner.core.model.Task

interface TaskLinkRepository {
    suspend fun getLinks(disciplineId: Long, taskId: Long): List<Task.Link>
    suspend fun createLink(disciplineId: Long, taskId: Long, request: Task.Link.CreateRequest): Task.Link
    suspend fun updateLink(disciplineId: Long, taskId: Long, id: Long, request: Task.Link.UpdateRequest): Task.Link
    suspend fun deleteLink(disciplineId: Long, taskId: Long, id: Long)
}
