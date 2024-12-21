package ru.ruscalworld.studyplanner.screens.editor.curriculum

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.repository.CurriculumRepository
import ru.ruscalworld.studyplanner.core.repository.DisciplineRepository
import ru.ruscalworld.studyplanner.screens.editor.discipline.DisciplineEditorViewModel
import ru.ruscalworld.studyplanner.settings.ActiveCurriculumStore
import ru.ruscalworld.studyplanner.ui.exceptions.VisibleException
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CurriculumEditorViewModel @Inject constructor(
    private val curriculumRepository: CurriculumRepository,
    private val disciplineRepository: DisciplineRepository,
    private val activeCurriculumStore: ActiveCurriculumStore,
) : ViewModel() {
    companion object {
        const val TAG = "CurriculumEditorViewModel"
    }

    val uiState = MutableStateFlow(CurriculumEditorState())

    val name = MutableStateFlow(TextFieldValue())
    val semesterNo = MutableStateFlow(TextFieldValue())

    init {
        viewModelScope.launch {
            combine(name, semesterNo) { name, semesterNo -> Pair(name, semesterNo) }
                .debounce(1000)
                .distinctUntilChanged()
                .collect { updateCurriculum() }
        }
    }

    fun load() {
        uiState.value = CurriculumEditorState(isLoading = true)

        viewModelScope.launch {
            try {
                val curriculumId = activeCurriculumStore.loadActiveCurriculum()
                    ?: throw VisibleException(R.string.editor_curriculum_error_no_curriculum)

                val curriculumFetcher = async { curriculumRepository.getCurriculum(curriculumId) }
                val disciplinesFetcher = async { disciplineRepository.getDisciplines(curriculumId) }

                val curriculum = curriculumFetcher.await()

                name.value = TextFieldValue(curriculum.name)
                semesterNo.value = TextFieldValue(curriculum.semester.toString())

                uiState.value = CurriculumEditorState(
                    isLoading = false,
                    curriculum = curriculum,
                    disciplines = disciplinesFetcher.await(),
                )
            } catch (e: Exception) {
                Log.e(TAG, "Data fetching failed", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

    private fun updateCurriculum() {
        viewModelScope.launch {
            val curriculum = uiState.value.curriculum
            Log.d(TAG, "updateCurriculum: ${curriculum?.id}, \"${name.value.text}\", ${semesterNo.value.text}")
            if (curriculum == null || !semesterNo.value.text.isDigitsOnly()) return@launch

            try {
                curriculumRepository.updateCurriculum(
                    curriculum.id,

                    Curriculum.UpdateRequest(
                        name.value.text,
                        semesterNo.value.text.toInt(),
                    )
                )
            } catch (e: Exception) {
                Log.e(DisciplineEditorViewModel.TAG, "Data update failed", e)
                uiState.update { it.copy(error = e) }
            }
        }
    }

    fun deleteCurriculum(then: () -> Unit) {
        viewModelScope.launch {
            val curriculum = uiState.value.curriculum
            Log.d(TAG, "deleteCurriculum: ${curriculum?.id}")
            if (curriculum == null) return@launch

            try {
                curriculumRepository.deleteCurriculum(curriculum.id)
                activeCurriculumStore.storeActiveCurriculum(null)
                then()
            } catch (e: Exception) {
                Log.e(TAG, "Deletion failed", e)
                uiState.update { it.copy(error = e) }
            }
        }
    }

    fun onNameChanged(value: TextFieldValue) {
        name.value = value
    }

    fun onSemesterNoChanged(value: TextFieldValue) {
        if (!value.text.isDigitsOnly()) return
        semesterNo.value = value
    }

    fun onDisciplineAdded(discipline: Discipline) {
        uiState.update { it.copy(disciplines = it.disciplines?.plus(discipline)) }
    }
}
