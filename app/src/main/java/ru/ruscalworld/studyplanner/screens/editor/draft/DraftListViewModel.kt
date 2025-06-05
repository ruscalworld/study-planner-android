package ru.ruscalworld.studyplanner.screens.editor.draft

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.repository.DraftRepository
import ru.ruscalworld.studyplanner.screens.diary.home.HomeState
import javax.inject.Inject

@HiltViewModel
class DraftListViewModel @Inject constructor(
    private val draftRepository: DraftRepository,
): ViewModel() {
    companion object {
        const val TAG = "HomeViewModel"
    }

    val uiState = MutableStateFlow(HomeState())

    fun load() {
        uiState.value = HomeState(isLoading = true)
        this.reload()
    }

    fun reload() {
        viewModelScope.launch {
            try {
                val draftsFetcher = async {
                    draftRepository.getDrafts()
                }

                val state = HomeState(
                    isLoading = false,
                    drafts = draftsFetcher.await()
                )

                uiState.value = state
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

    fun onDraftSaved() {
        this.reload()
    }

    fun onDraftDeleted() {
        this.reload()
    }
}