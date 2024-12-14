package ru.ruscalworld.studyplanner.forms.link.create

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.core.model.Discipline

@Composable
fun CreateDisciplineLinkModal(
    viewModel: CreateDisciplineLinkModalViewModel = hiltViewModel(),
    modalOpen: Boolean,
    linkFactory: suspend (CreateLinkRequest) -> Discipline.Link,
    onClosed: () -> Unit,
    onLinkCreated: (Discipline.Link) -> Unit,
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
