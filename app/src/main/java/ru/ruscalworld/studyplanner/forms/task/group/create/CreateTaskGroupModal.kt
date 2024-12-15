package ru.ruscalworld.studyplanner.forms.task.group.create

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.ui.elements.field.Field
import ru.ruscalworld.studyplanner.ui.elements.form.BottomSheetForm

@Composable
fun CreateTaskGroupModal(
    viewModel: CreateTaskGroupModalViewModel = hiltViewModel(),
    disciplineId: Long,
    modalOpen: Boolean,
    onClosed: () -> Unit = {},
    onTaskGroupCreated: (Task.Group) -> Unit = {},
    snackbarHostState: SnackbarHostState,
) {
    val state by viewModel.uiState.collectAsState()
    var name by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(state.taskGroup) {
        state.taskGroup?.let {
            name = TextFieldValue()
            onTaskGroupCreated(it)
        }
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.form_task_group_create_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    BottomSheetForm(
        confirmText = { stringResource(R.string.form_task_group_create_confirm_text) },
        isVisible = modalOpen,
        isLoading = state.isLoading,
        onConfirm = {
            viewModel.createTaskGroup(disciplineId, Task.Group.CreateRequest(name.text))
            onClosed()
        },
        titleText = { stringResource(R.string.form_task_group_create_title) },
        onClosed = onClosed,
    ) {
        Field(
            value = name,
            onValueChange = { name = it },
            label = { stringResource(R.string.form_task_group_create_name_label) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
