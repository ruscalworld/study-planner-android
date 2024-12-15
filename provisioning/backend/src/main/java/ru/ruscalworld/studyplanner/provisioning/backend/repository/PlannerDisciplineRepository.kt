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
import ru.ruscalworld.studyplanner.core.model.GenericStats
import ru.ruscalworld.studyplanner.core.repository.DisciplineRepository
import ru.ruscalworld.studyplanner.provisioning.backend.PlannerClient
import ru.ruscalworld.studyplanner.provisioning.backend.dto.ListDTO
import ru.ruscalworld.studyplanner.provisioning.backend.dto.common.GenericStatsDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline.CreateDisciplineRequestDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline.DisciplineDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.discipline.UpdateDisciplineRequestDto

class PlannerDisciplineRepository(private val client: PlannerClient) : DisciplineRepository {
    override suspend fun getDisciplines(curriculumId: Long): List<Discipline> {
        val disciplineList: List<DisciplineDto> = client.httpClient.get(
            "curriculums/$curriculumId/disciplines"
        ).body()

        return ListDTO(disciplineList).toInternalObject()
    }

    override suspend fun getDiscipline(curriculumId: Long, id: Long): Discipline {
        val discipline: DisciplineDto = client.httpClient.get(
            "curriculums/$curriculumId/disciplines/$id"
        ).body()

        return discipline.toInternalObject()
    }

    override suspend fun createDiscipline(
        curriculumId: Long,
        request: Discipline.CreateRequest,
    ): Discipline {
        val discipline: DisciplineDto = client.httpClient.post(
            "curriculums/$curriculumId/disciplines"
        ) {
            contentType(ContentType.Application.Json)
            setBody(CreateDisciplineRequestDto(request))
        }.body()

        return discipline.toInternalObject()
    }

    override suspend fun updateDiscipline(
        curriculumId: Long,
        id: Long,
        request: Discipline.UpdateRequest,
    ): Discipline {
        val discipline: DisciplineDto = client.httpClient.put(
            "curriculums/$curriculumId/disciplines/$id"
        ) {
            contentType(ContentType.Application.Json)
            setBody(UpdateDisciplineRequestDto(request))
        }.body()

        return discipline.toInternalObject()
    }

    override suspend fun deleteDiscipline(curriculumId: Long, id: Long) {
        client.httpClient.delete("curriculums/$curriculumId/disciplines/$id")
    }

    override suspend fun getState(id: Long): GenericStats {
        val stats: GenericStatsDto = client.httpClient.get(
            "disciplines/$id/stats"
        ).body()

        return stats.toInternalObject()
    }
}
