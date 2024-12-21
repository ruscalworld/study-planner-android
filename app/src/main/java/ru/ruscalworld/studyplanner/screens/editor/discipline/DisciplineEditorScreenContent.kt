package ru.ruscalworld.studyplanner.screens.editor.discipline

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.Link
import ru.ruscalworld.studyplanner.common.AddLinkButton
import ru.ruscalworld.studyplanner.common.InputGroup
import ru.ruscalworld.studyplanner.common.LinkRow
import ru.ruscalworld.studyplanner.ui.elements.card.CardButton
import ru.ruscalworld.studyplanner.ui.elements.card.ConfirmationButton
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.elements.field.Field
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun DisciplineEditorScreenContent(
    viewModel: DisciplineEditorViewModel = hiltViewModel(),
    disciplineId: Long,
    navigateToTask: (Long, Long) -> Unit,
    snackbarHostState: SnackbarHostState,
    onCreateLinkRequest: () -> Unit,
    onCreateTaskGroupRequest: () -> Unit,
    onDeleteRequest: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val name by viewModel.name.collectAsState()

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

        AddLinkButton(onClick = { onCreateLinkRequest() })
    }

    state.taskGroups?.let {
        for (group in it) {
            val tasks = state.tasks
            val groupId = group.id

            if (tasks == null) continue

            TaskGroupBlock(
                disciplineId = disciplineId,
                taskGroup = group,
                tasks = tasks[groupId] ?: listOf(),
                onTaskCreated = { task -> viewModel.onTaskCreated(task) },
                snackbarHostState = snackbarHostState,
                navigateToTask = { taskId -> navigateToTask(disciplineId, taskId) },
                onDeleteRequest = { viewModel.deleteTaskGroup(disciplineId, group) }
            )
        }

        CardButton(
            onClick = { onCreateTaskGroupRequest() },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.fa_plus_solid),
                    tint = PrimaryText,
                    contentDescription = null,
                )
            }
        ) {
            stringResource(R.string.editor_discipline_tasks_groups_create)
        }
    }

    ConfirmationButton(
        onConfirm = onDeleteRequest,
        content = { stringResource(R.string.editor_discipline_delete_button) },
        confirmContent = { stringResource(R.string.editor_discipline_delete_confirm_button) },
        icon = {
            Icon(
                painter = painterResource(R.drawable.fa_trash_can_solid),
                tint = PrimaryText,
                contentDescription = null,
            )
        }
    )
}
