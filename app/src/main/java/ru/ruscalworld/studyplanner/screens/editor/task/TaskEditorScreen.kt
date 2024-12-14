package ru.ruscalworld.studyplanner.screens.editor.task

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
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.InputGroup
import ru.ruscalworld.studyplanner.common.LinkRow
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.common.TextFieldRow
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.forms.link.create.CreateTaskLinkModal
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.elements.field.DateField
import ru.ruscalworld.studyplanner.ui.elements.field.Field

@Composable
fun TaskEditorScreen(
    viewModel: TaskEditorViewModel = hiltViewModel(),
    disciplineId: Long,
    taskId: Long,
) {
    val state by viewModel.uiState.collectAsState()

    val name by viewModel.name.collectAsState()
    val externalName by viewModel.externalName.collectAsState()
    val description by viewModel.description.collectAsState()
    val difficulty by viewModel.difficulty.collectAsState()

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

        return
    }

    CommonLayout {
        Headline(
            title = { stringResource(R.string.editor_task_title).toUpperCase(Locale.current) },
            highlight = true,
        )

        InputGroup {
            Field(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = { stringResource(R.string.editor_task_name_label) },
            )

            Field(
                modifier = Modifier.fillMaxWidth(),
                value = externalName,
                onValueChange = { viewModel.onExternalNameChanged(it) },
                label = { stringResource(R.string.editor_task_external_name_label) },
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

        Field(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            label = { stringResource(R.string.editor_task_description_label) },
            lines = 5,
        )

        InputGroup {
            TextFieldRow(
                labelWeight = 1f,
                label = { stringResource(R.string.editor_task_difficulty_label) },
            ) {
                Field(
                    modifier = Modifier.fillMaxWidth(),
                    value = difficulty,
                    onValueChange = { viewModel.onDifficultyChanged(it) },
                )
            }

            TextFieldRow(
                labelWeight = 1f,
                label = { stringResource(R.string.editor_task_deadline_label) },
            ) {
                DateField(
                    modifier = Modifier.fillMaxWidth(),
                    onDatePicked = { viewModel.onDeadlineChanged(it) },
                    confirmText = R.string.editor_task_deadline_pick_confirm,
                    dismissText = R.string.editor_task_deadline_pick_dismiss,
                )
            }
        }
    }

    CreateTaskLinkModal(
        modalOpen = createLinkModalOpen,
        linkFactory = { request -> Task.Link(1, request.name, request.url) },
        onClosed = { createLinkModalOpen = false },
        onLinkCreated = { link -> viewModel.onLinkCreated(link) },
        snackbarHostState = snackbarHostState,
    )
}
