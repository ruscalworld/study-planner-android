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
import ru.ruscalworld.studyplanner.core.repository.TaskGroupRepository
import ru.ruscalworld.studyplanner.provisioning.backend.PlannerClient
import ru.ruscalworld.studyplanner.provisioning.backend.dto.ListDTO
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.CreateTaskGroupRequestDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.TaskGroupDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.UpdateTaskGroupRequestDto

class PlannerTaskGroupRepository(val client: PlannerClient) : TaskGroupRepository {
    override suspend fun getGroup(disciplineId: Long, taskGroupId: Long): Task.Group {
        val group: TaskGroupDto = client.httpClient.get(
            "disciplines/$disciplineId/groups/$taskGroupId"
        ).body()

        return group.toInternalObject()
    }

    override suspend fun getGroups(disciplineId: Long): List<Task.Group> {
        val groups: List<TaskGroupDto> = client.httpClient.get(
            "disciplines/$disciplineId/groups"
        ).body()

        return ListDTO(groups).toInternalObject()
    }

    override suspend fun createGroup(
        disciplineId: Long,
        request: Task.Group.CreateRequest
    ): Task.Group {
        val group: TaskGroupDto = client.httpClient.post(
            "disciplines/$disciplineId/groups"
        ) {
            contentType(ContentType.Application.Json)
            setBody(CreateTaskGroupRequestDto(request))
        }.body()

        return group.toInternalObject()
    }

    override suspend fun updateGroup(
        disciplineId: Long,
        taskGroupId: Long,
        request: Task.Group.UpdateRequest
    ): Task.Group {
        val group: TaskGroupDto = client.httpClient.put(
            "disciplines/$disciplineId/groups/$taskGroupId"
        ) {
            contentType(ContentType.Application.Json)
            setBody(UpdateTaskGroupRequestDto(request))
        }.body()

        return group.toInternalObject()
    }

    override suspend fun deleteGroup(disciplineId: Long, taskGroupId: Long) {
        client.httpClient.delete("disciplines/$disciplineId/groups/$taskGroupId")
    }
}
