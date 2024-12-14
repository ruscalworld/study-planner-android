package ru.ruscalworld.studyplanner.forms.link.create

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Task

@Composable
fun CreateTaskLinkModal(
    viewModel: CreateTaskLinkModalViewModel = hiltViewModel(),
    modalOpen: Boolean,
    linkFactory: suspend (CreateLinkRequest) -> Task.Link,
    onClosed: () -> Unit,
    onLinkCreated: (Task.Link) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    CreateLinkModal(
        viewModel = viewModel,
        modalOpen = modalOpen,
        linkFactory = linkFactory,
        onClosed = onClosed,
        onLinkCreated = onLinkCreated,
        snackbarHostState = snackbarHostState,
    )
}
