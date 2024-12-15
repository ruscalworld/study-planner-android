package ru.ruscalworld.studyplanner.forms.task.create

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
fun CreateTaskModal(
    viewModel: CreateTaskModalViewModel = hiltViewModel(),
    disciplineId: Long,
    taskGroupId: Long,
    modalOpen: Boolean,
    onClosed: () -> Unit = {},
    onTaskCreated: (Task) -> Unit = {},
    snackbarHostState: SnackbarHostState,
) {
    val state by viewModel.uiState.collectAsState()
    var name by remember { mutableStateOf(TextFieldValue()) }
    var externalName by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(state.task) {
        state.task?.let {
            name = TextFieldValue()
            externalName = TextFieldValue()
        }
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.form_task_create_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    BottomSheetForm(
        confirmText = { stringResource(R.string.form_task_create_confirm_text) },
        isVisible = modalOpen,
        isLoading = state.isLoading,
        onConfirm = {
            viewModel.createTask(disciplineId, taskGroupId, Task.CreateRequest(
                name.text,
                externalName.text.ifBlank { null },
                null,
                2,
            ), onTaskCreated)

            onClosed()
        },
        titleText = { stringResource(R.string.form_task_create_title) },
        onClosed = onClosed,
    ) {
        Field(
            value = name,
            onValueChange = { name = it },
            label = { stringResource(R.string.form_task_create_title_label) },
            modifier = Modifier.fillMaxWidth(),
        )

        Field(
            value = externalName,
            onValueChange = { externalName = it },
            label = { stringResource(R.string.form_task_create_external_name_label) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
