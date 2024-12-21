package ru.ruscalworld.studyplanner.screens.editor.curriculum

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.common.LoadingVisibility
import ru.ruscalworld.studyplanner.forms.discipline.create.CreateDisciplineModal

@Composable
fun CurriculumEditorScreen(
    viewModel: CurriculumEditorViewModel = hiltViewModel(),
    navigateToDiscipline: (Long) -> Unit,
    navigateToOptions: () -> Unit,
    scaffoldPadding: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var disciplineModalOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.editor_discipline_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.editor_curriculum_loading_title) },
            description = { stringResource(R.string.editor_curriculum_loading_description) },
        )
    }

    LoadingVisibility(isLoading = state.isLoading) {
        CommonLayout(scaffoldPadding = scaffoldPadding) {
            CurriculumEditorScreenContent(
                navigateToDiscipline = navigateToDiscipline,
                onDisciplineCreateRequest = { disciplineModalOpen = true },
                onDeleteRequest = { viewModel.deleteCurriculum { navigateToOptions() } },
            )
        }
    }

    state.curriculum?.let {
        CreateDisciplineModal(
            curriculumId = it.id,
            snackbarHostState = snackbarHostState,
            modalOpen = disciplineModalOpen,
            onClosed = { disciplineModalOpen = false },
            onDisciplineCreated = { discipline ->
                viewModel.onDisciplineAdded(discipline)
                disciplineModalOpen = false
            },
        )
    }

    SnackbarHost(hostState = snackbarHostState)
}
