package ru.ruscalworld.studyplanner.screens.editor.task

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
import ru.ruscalworld.studyplanner.forms.link.create.CreateTaskLinkModal

@Composable
fun TaskEditorScreen(
    viewModel: TaskEditorViewModel = hiltViewModel(),
    disciplineId: Long,
    taskId: Long,
    scaffoldPadding: PaddingValues,
    navigateBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    var createLinkModalOpen by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(disciplineId, taskId) {
        viewModel.load(disciplineId, taskId)
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.editor_task_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.editor_task_loading_title) },
            description = { stringResource(R.string.editor_task_loading_description) },
        )
    }

    LoadingVisibility(isLoading = state.isLoading) {
        CommonLayout(scaffoldPadding = scaffoldPadding) {
            TaskEditorScreenContent(
                onCreateLinkRequest = { createLinkModalOpen = true },
                onDeleteRequest = { viewModel.deleteTask(disciplineId, taskId) { navigateBack() } },
            )
        }
    }

    CreateTaskLinkModal(
        modalOpen = createLinkModalOpen,
        linkFactory = { request -> viewModel.createLink(disciplineId, request) },
        onClosed = { createLinkModalOpen = false },
        onLinkCreated = { link -> viewModel.onLinkCreated(link) },
        snackbarHostState = snackbarHostState,
    )

    SnackbarHost(hostState = snackbarHostState)
}
