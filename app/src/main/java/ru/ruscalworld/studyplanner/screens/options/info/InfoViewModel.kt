package ru.ruscalworld.studyplanner.screens.options.info

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.core.repository.CurriculumRepository
import ru.ruscalworld.studyplanner.provisioning.backend.CredentialsSupplier
import ru.ruscalworld.studyplanner.screens.editor.discipline.DisciplineEditorViewModel.Companion.TAG
import ru.ruscalworld.studyplanner.settings.ActiveCurriculumStore
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val curriculumRepository: CurriculumRepository,
    private val credentialsSupplier: CredentialsSupplier,
    private val activeCurriculumStore: ActiveCurriculumStore,
) : ViewModel() {
    val uiState = MutableStateFlow(InfoState())

    fun load() {
        uiState.value = InfoState(isLoading = true)

        viewModelScope.launch {
            try {
                val curriculumFetcher = async { curriculumRepository.getDisciplines() }

                val state = InfoState(
                    isLoading = false,
                    curriculums = curriculumFetcher.await(),
                )

                uiState.value = state
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            credentialsSupplier.clearCredentials()
            uiState.update { it.copy(signedOut = true) }
        }
    }

    fun pickCurriculum(curriculum: Curriculum) {
        viewModelScope.launch {
            activeCurriculumStore.storeActiveCurriculum(curriculum.id)
            uiState.update { it.copy(activeCurriculumChanged = true) }
        }
    }
}
