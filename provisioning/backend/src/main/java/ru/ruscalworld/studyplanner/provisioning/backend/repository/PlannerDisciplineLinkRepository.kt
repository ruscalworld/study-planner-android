package ru.ruscalworld.studyplanner.provisioning.backend.repository

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.repository.DisciplineLinkRepository
import ru.ruscalworld.studyplanner.provisioning.backend.PlannerClient
import ru.ruscalworld.studyplanner.provisioning.backend.dto.ListDTO
import ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline.CreateDisciplineLinkRequestDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline.DisciplineLinkDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline.UpdateDisciplineLinkRequestDto

class PlannerDisciplineLinkRepository(
    private val client: PlannerClient,
) : DisciplineLinkRepository {
    override suspend fun getLinks(disciplineId: Long): List<Discipline.Link> {
        val links: List<DisciplineLinkDto> = client.httpClient.get(
            "disciplines/$disciplineId/links"
        ).body()

        return ListDTO(links).toInternalObject()
    }

    override suspend fun createLink(
        disciplineId: Long,
        request: Discipline.Link.CreateRequest
    ): Discipline.Link {
        val link: DisciplineLinkDto = client.httpClient.post(
            "disciplines/$disciplineId/links"
        ) {
            contentType(ContentType.Application.Json)
            setBody(CreateDisciplineLinkRequestDto(request))
        }.body()

        return link.toInternalObject()
    }

    override suspend fun updateLink(
        disciplineId: Long,
        id: Long,
        request: Discipline.Link.UpdateRequest
    ): Discipline.Link {
        val link: DisciplineLinkDto = client.httpClient.put(
            "disciplines/$disciplineId/links/$id"
        ) {
            contentType(ContentType.Application.Json)
            setBody(UpdateDisciplineLinkRequestDto(request))
        }.body()

        return link.toInternalObject()
    }

    override suspend fun deleteLink(disciplineId: Long, id: Long) {
        client.httpClient.delete("disciplines/$disciplineId/links/$id")
    }
}
