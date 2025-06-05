package ru.ruscalworld.studyplanner.screens.editor.draft

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
import ru.ruscalworld.studyplanner.core.model.Draft
import ru.ruscalworld.studyplanner.forms.draft.update.UpdateDraftModal

@Composable
fun DraftListScreen(
    viewModel: DraftListViewModel = hiltViewModel(),
    scaffoldPadding: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()
    var draftCreating by remember { mutableStateOf(false) }
    var editedDraft by remember { mutableStateOf<Draft?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

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
            title = { stringResource(R.string.editor_discipline_loading_title) },
            description = { stringResource(R.string.editor_discipline_loading_description) },
        )
    }

    LoadingVisibility(isLoading = state.isLoading) {
        CommonLayout(scaffoldPadding = scaffoldPadding) {
            DraftListScreenContent(
                onEditRequest = { editedDraft = it },
                onCreateRequest = { draftCreating = true },
            )
        }
    }

    UpdateDraftModal(
        modalOpen = draftCreating || editedDraft != null,
        onClosed = {
            draftCreating = false
            editedDraft = null
        },
        draft = editedDraft,
        onDraftSaved = { draft -> viewModel.onDraftSaved() },
        onDraftDeleted = { viewModel.onDraftDeleted() },
        snackbarHostState = snackbarHostState,
    )

    SnackbarHost(hostState = snackbarHostState)
}
