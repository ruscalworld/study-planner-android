package ru.ruscalworld.studyplanner.forms.draft.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.Draft
import ru.ruscalworld.studyplanner.core.repository.DraftRepository
import javax.inject.Inject

@HiltViewModel
class UpdateDraftModalViewModel @Inject constructor(
    private val draftRepository: DraftRepository,
) : ViewModel() {
    companion object {
        const val TAG = "UpdateDraftModalViewModel"
    }

    val uiState = MutableStateFlow(UpdateDraftState())

    fun createDraft(request: Draft.CreateRequest) {
        uiState.update { UpdateDraftState(isLoading = true) }

        viewModelScope.launch {
            try {
                val draft = draftRepository.createDraft(request)
                uiState.update { it.copy(isLoading = false, error = null, draft = draft) }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to create draft", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

    fun updateDraft(id: Long, request: Draft.UpdateRequest) {
        uiState.update { UpdateDraftState(isLoading = true) }

        viewModelScope.launch {
            try {
                val draft = draftRepository.updateDraft(id, request)
                uiState.update { it.copy(isLoading = false, error = null, draft = draft) }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to update draft", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

    fun deleteDraft(id: Long) {
        uiState.update { UpdateDraftState(isLoading = true) }

        viewModelScope.launch {
            try {
                draftRepository.deleteDraft(id)
                uiState.update { it.copy(isLoading = false, error = null, draft = null, isDeleted = true) }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to delete draft", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }
}
