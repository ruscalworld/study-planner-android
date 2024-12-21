package ru.ruscalworld.studyplanner.screens.editor.discipline

import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.forms.task.create.CreateTaskModal
import ru.ruscalworld.studyplanner.ui.elements.card.CardButton
import ru.ruscalworld.studyplanner.ui.elements.card.ConfirmationButton
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun TaskGroupBlock(
    disciplineId: Long,
    taskGroup: Task.Group,
    tasks: List<Task>,
    navigateToTask: (Long) -> Unit,
    onTaskCreated: (Task) -> Unit,
    onDeleteRequest: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    var createTaskModalOpen by remember { mutableStateOf(false) }

    NamedCardContainer(
        title = { taskGroup.name },
    ) {
        for (task in tasks) {
            TaskCard(task, navigateToTask = { taskId -> navigateToTask(taskId) })
        }

        CardButton(
            onClick = { createTaskModalOpen = true },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.fa_plus_solid),
                    tint = PrimaryText,
                    contentDescription = null,
                )
            }
        ) {
            stringResource(R.string.editor_discipline_tasks_create)
        }

        ConfirmationButton(
            onConfirm = onDeleteRequest,
            content = { stringResource(R.string.editor_discipline_tasks_groups_delete_button) },
            confirmContent = { stringResource(R.string.editor_discipline_tasks_groups_delete_confirm_button) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.fa_trash_can_solid),
                    tint = PrimaryText,
                    contentDescription = null,
                )
            }
        )
    }

    CreateTaskModal(
        modalOpen = createTaskModalOpen,
        onClosed = { createTaskModalOpen = false },
        disciplineId = disciplineId,
        taskGroupId = taskGroup.id,
        onTaskCreated = onTaskCreated,
        snackbarHostState = snackbarHostState,
    )
}
