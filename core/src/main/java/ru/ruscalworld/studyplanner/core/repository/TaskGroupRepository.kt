package ru.ruscalworld.studyplanner.core.repository

import ru.ruscalworld.studyplanner.core.model.Task

interface TaskGroupRepository {
    suspend fun getGroup(disciplineId: Long, taskGroupId: Long): Task.Group
    suspend fun getGroups(disciplineId: Long): List<Task.Group>
    suspend fun createGroup(disciplineId: Long, request: Task.Group.CreateRequest): Task.Group
    suspend fun updateGroup(disciplineId: Long, taskGroupId: Long, request: Task.Group.UpdateRequest): Task.Group
    suspend fun deleteGroup(disciplineId: Long, taskGroupId: Long)
}
