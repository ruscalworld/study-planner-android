package ru.ruscalworld.studyplanner.core.repository

import ru.ruscalworld.studyplanner.core.model.Draft
import ru.ruscalworld.studyplanner.core.model.Task

interface DraftRepository {
    suspend fun getDrafts(): List<Draft>
    suspend fun createDraft(request: Draft.CreateRequest): Draft
    suspend fun updateDraft(id: Long, request: Draft.UpdateRequest): Draft
    suspend fun deleteDraft(id: Long)

    suspend fun moveDraft(id: Long, request: Draft.MoveRequest): Task
}
