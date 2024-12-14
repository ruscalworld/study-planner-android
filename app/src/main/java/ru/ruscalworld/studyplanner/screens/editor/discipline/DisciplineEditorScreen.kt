package ru.ruscalworld.studyplanner.screens.editor.discipline

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.Link
import ru.ruscalworld.studyplanner.common.AddLinkButton
import ru.ruscalworld.studyplanner.common.LinkRow
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.InputGroup
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.forms.link.create.CreateDisciplineLinkModal
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.elements.field.Field

@Composable
fun DisciplineEditorScreen(
    viewModel: DisciplineEditorViewModel = hiltViewModel(),
    disciplineId: Long,
    navigateToTask: (Long, Long) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val name by viewModel.name.collectAsState()
    var createLinkModalOpen by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(disciplineId) {
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

        return
    }

    CommonLayout {
        Headline(
            title = { stringResource(R.string.editor_discipline_title).toUpperCase(Locale.current) },
            highlight = true,
        )

        InputGroup {
            Field(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = { stringResource(R.string.editor_discipline_name_label) },
            )
        }

        LinkRow {
            state.links?.let {
                for (link in it) {
                    Link(link = link)
                }
            }

            AddLinkButton(onClick = { createLinkModalOpen = true })
        }

        state.taskGroups?.let {
            for (group in it) {
                NamedCardContainer(
                    title = { group.name },
                ) {
                    if (state.tasks == null || group.id !in state.tasks!!) return@NamedCardContainer

                    for (task in state.tasks!![group.id]!!) {
                        TaskCard(task, navigateToTask = { taskId -> navigateToTask(disciplineId, taskId) })
                    }
                }
            }
        }
    }

    CreateDisciplineLinkModal(
        modalOpen = createLinkModalOpen,
        linkFactory = { request -> Discipline.Link(1, request.name, request.url) },
        onClosed = { createLinkModalOpen = false },
        onLinkCreated = { link -> viewModel.onLinkCreated(link) },
        snackbarHostState = snackbarHostState,
    )
}
