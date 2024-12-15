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
import ru.ruscalworld.studyplanner.core.repository.TaskLinkRepository
import ru.ruscalworld.studyplanner.provisioning.backend.PlannerClient
import ru.ruscalworld.studyplanner.provisioning.backend.dto.ListDTO
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.CreateTaskLinkRequestDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.TaskLinkDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.UpdateTaskLinkRequestDto

class PlannerTaskLinkRepository(
    private val client: PlannerClient,
) : TaskLinkRepository {
    override suspend fun getLinks(disciplineId: Long, taskId: Long): List<Task.Link> {
        val links: List<TaskLinkDto> = client.httpClient.get(
            "disciplines/$disciplineId/tasks/$taskId/links"
        ).body()

        return ListDTO(links).toInternalObject()
    }

    override suspend fun createLink(
        disciplineId: Long,
        taskId: Long,
        request: Task.Link.CreateRequest
    ): Task.Link {
        val link: TaskLinkDto = client.httpClient.post(
            "disciplines/$disciplineId/tasks/$taskId/links"
        ) {
            contentType(ContentType.Application.Json)
            setBody(CreateTaskLinkRequestDto(request))
        }.body()

        return link.toInternalObject()
    }

    override suspend fun updateLink(
        disciplineId: Long,
        taskId: Long,
        id: Long,
        request: Task.Link.UpdateRequest
    ): Task.Link {
        val link: TaskLinkDto = client.httpClient.put(
            "disciplines/$disciplineId/tasks/$taskId/links/$id"
        ) {
            contentType(ContentType.Application.Json)
            setBody(UpdateTaskLinkRequestDto(request))
        }.body()

        return link.toInternalObject()
    }

    override suspend fun deleteLink(disciplineId: Long, taskId: Long, id: Long) {
        client.httpClient.delete("disciplines/$disciplineId/tasks/$taskId/links/$id")
    }
}
