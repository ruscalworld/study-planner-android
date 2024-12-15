package ru.ruscalworld.studyplanner.provisioning.backend.repository

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.repository.TaskRepository
import ru.ruscalworld.studyplanner.provisioning.backend.PlannerClient
import ru.ruscalworld.studyplanner.provisioning.backend.dto.ListDTO
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.CreateTaskRequestDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.TaskDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.UpdateTaskRequestDto

class PlannerTaskRepository(val client: PlannerClient) : TaskRepository {
    override suspend fun getTask(disciplineId: Long, taskId: Long): Task {
        val task: TaskDto = client.httpClient.get(
            "disciplines/$disciplineId/tasks/$taskId"
        ).body()

        return task.toInternalObject()
    }

    override suspend fun getTasks(disciplineId: Long): List<Task> {
        val tasks: List<TaskDto> = client.httpClient.get(
            "disciplines/$disciplineId/tasks"
        ).body()

        return ListDTO(tasks).toInternalObject()
    }

    override suspend fun createTask(
        disciplineId: Long,
        taskGroupId: Long,
        request: Task.CreateRequest,
    ): Task {
        val task: TaskDto = client.httpClient.post(
            "disciplines/$disciplineId/groups/$taskGroupId/tasks"
        ) {
            contentType(ContentType.Application.Json)
            setBody(CreateTaskRequestDto(request))
        }.body()

        return task.toInternalObject()
    }

    override suspend fun updateTask(
        disciplineId: Long,
        taskId: Long,
        request: Task.UpdateRequest,
    ): Task {
        val task: TaskDto = client.httpClient.put(
            "disciplines/$disciplineId/tasks/$taskId"
        ) {
            contentType(ContentType.Application.Json)
            setBody(UpdateTaskRequestDto(request))
        }.body()

        return task.toInternalObject()
    }

    override suspend fun deleteTask(disciplineId: Long, taskId: Long) {
        client.httpClient.delete("disciplines/$disciplineId/tasks/$taskId")
    }
}
