package ru.ruscalworld.studyplanner.provisioning.backend.repository

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.core.repository.CurriculumRepository
import ru.ruscalworld.studyplanner.provisioning.backend.PlannerClient
import ru.ruscalworld.studyplanner.provisioning.backend.dto.ListDTO
import ru.ruscalworld.studyplanner.provisioning.backend.dto.curriculum.CreateCurriculumRequestDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.curriculum.CurriculumDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.curriculum.UpdateCurriculumRequestDto

class PlannerCurriculumRepository(private val client: PlannerClient) : CurriculumRepository {
    override suspend fun getCurriculums(): List<Curriculum> {
        val curriculumList: List<CurriculumDto> = client.httpClient.get("profile/curriculums").body()
        return ListDTO(curriculumList).toInternalObject()
    }

    override suspend fun getCurriculum(id: Long): Curriculum {
        val curriculum: CurriculumDto = client.httpClient.get("curriculums/$id").body()
        return curriculum.toInternalObject()
    }

    override suspend fun createCurriculum(request: Curriculum.CreateRequest): Curriculum {
        val curriculum: CurriculumDto = client.httpClient.post("curriculums") {
            contentType(ContentType.Application.Json)
            setBody(CreateCurriculumRequestDto(request))
        }.body()

        return curriculum.toInternalObject()
    }

    override suspend fun updateCurriculum(id: Long, request: Curriculum.UpdateRequest): Curriculum {
        val curriculum: CurriculumDto = client.httpClient.put("curriculums/$id") {
            contentType(ContentType.Application.Json)
            setBody(UpdateCurriculumRequestDto(request))
        }.body()

        return curriculum.toInternalObject()
    }

    override suspend fun deleteCurriculum(id: Long) {
        client.httpClient.delete("curriculums/$id")
    }
}
