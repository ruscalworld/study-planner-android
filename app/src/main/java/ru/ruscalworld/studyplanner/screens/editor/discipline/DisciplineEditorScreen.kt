package ru.ruscalworld.studyplanner.screens.editor.discipline

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
import ru.ruscalworld.studyplanner.forms.link.create.CreateDisciplineLinkModal
import ru.ruscalworld.studyplanner.forms.task.group.create.CreateTaskGroupModal

@Composable
fun DisciplineEditorScreen(
    viewModel: DisciplineEditorViewModel = hiltViewModel(),
    disciplineId: Long,
    navigateToTask: (Long, Long) -> Unit,
    scaffoldPadding: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()
    var createLinkModalOpen by remember { mutableStateOf(false) }
    var createTaskGroupModalOpen by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(disciplineId) {
        viewModel.load(disciplineId)
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
            DisciplineEditorScreenContent(
                disciplineId = disciplineId,
                navigateToTask = navigateToTask,
                snackbarHostState = snackbarHostState,
                onCreateLinkRequest = { createLinkModalOpen = true },
                onCreateTaskGroupRequest = { createTaskGroupModalOpen = true },
            )
        }
    }

    CreateDisciplineLinkModal(
        modalOpen = createLinkModalOpen,
        linkFactory = { request -> viewModel.createLink(request) },
        onClosed = { createLinkModalOpen = false },
        onLinkCreated = { link -> viewModel.onLinkCreated(link) },
        snackbarHostState = snackbarHostState,
    )

    CreateTaskGroupModal(
        modalOpen = createTaskGroupModalOpen,
        onClosed = { createTaskGroupModalOpen = false },
        disciplineId = disciplineId,
        onTaskGroupCreated = { taskGroup -> viewModel.onTaskGroupCreated(taskGroup) },
        snackbarHostState = snackbarHostState,
    )

    SnackbarHost(hostState = snackbarHostState)
}
