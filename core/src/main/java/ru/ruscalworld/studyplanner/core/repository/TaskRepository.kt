package ru.ruscalworld.studyplanner.core.repository

import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.model.TaskProgress

interface TaskRepository {
    suspend fun getTask(disciplineId: Long, taskId: Long): Task
    suspend fun getTasks(disciplineId: Long): List<Task>
    suspend fun createTask(disciplineId: Long, taskGroupId: Long, request: Task.CreateRequest): Task
    suspend fun updateTask(disciplineId: Long, taskId: Long, request: Task.UpdateRequest): Task
    suspend fun deleteTask(disciplineId: Long, taskId: Long)

    suspend fun getTaskProgress(disciplineId: Long, taskId: Long): TaskProgress
    suspend fun updateTaskProgress(
        disciplineId: Long,
        taskId: Long,
        progress: TaskProgress
    ): TaskProgress
}
