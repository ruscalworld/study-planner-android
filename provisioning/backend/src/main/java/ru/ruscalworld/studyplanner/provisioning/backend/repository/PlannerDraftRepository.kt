package ru.ruscalworld.studyplanner.provisioning.backend.repository

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.ruscalworld.studyplanner.core.model.Draft
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.repository.DraftRepository
import ru.ruscalworld.studyplanner.provisioning.backend.PlannerClient
import ru.ruscalworld.studyplanner.provisioning.backend.dto.ListDTO
import ru.ruscalworld.studyplanner.provisioning.backend.dto.draft.CreateDraftRequestDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.draft.DraftDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.draft.MoveDraftRequestDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.draft.UpdateDraftRequestDto
import ru.ruscalworld.studyplanner.provisioning.backend.dto.task.TaskDto

class PlannerDraftRepository(private val client: PlannerClient) : DraftRepository {
    override suspend fun getDrafts(): List<Draft> {
        val draftList: List<DraftDto> = client.httpClient.get(
            "drafts"
        ).body()

        return ListDTO(draftList).toInternalObject()
    }

    override suspend fun createDraft(request: Draft.CreateRequest): Draft {
        val draft: DraftDto = client.httpClient.post(
            "drafts"
        ) {
            contentType(ContentType.Application.Json)
            setBody(CreateDraftRequestDto(request))
        }.body()

        return draft.toInternalObject()
    }

    override suspend fun updateDraft(
        id: Long,
        request: Draft.UpdateRequest
    ): Draft {
        val draft: DraftDto = client.httpClient.put(
            "drafts/$id"
        ) {
            contentType(ContentType.Application.Json)
            setBody(UpdateDraftRequestDto(request))
        }.body()

        return draft.toInternalObject()
    }

    override suspend fun deleteDraft(id: Long) {
        client.httpClient.delete("drafts/$id")
    }

    override suspend fun moveDraft(
        id: Long,
        request: Draft.MoveRequest
    ): Task {
        val task: TaskDto = client.httpClient.post(
            "drafts/$id/task"
        ) {
            contentType(ContentType.Application.Json)
            setBody(MoveDraftRequestDto(request))
        }.body()

        return task.toInternalObject()
    }
}
