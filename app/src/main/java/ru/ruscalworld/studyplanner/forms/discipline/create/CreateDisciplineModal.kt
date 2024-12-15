package ru.ruscalworld.studyplanner.forms.discipline.create

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
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.ui.elements.field.Field
import ru.ruscalworld.studyplanner.ui.elements.form.BottomSheetForm

@Composable
fun CreateDisciplineModal(
    viewModel: CreateDisciplineModalViewModel = hiltViewModel(),
    curriculumId: Long,
    modalOpen: Boolean,
    onClosed: () -> Unit = {},
    onDisciplineCreated: (Discipline) -> Unit = {},
    snackbarHostState: SnackbarHostState,
) {
    val state by viewModel.uiState.collectAsState()
    var name by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(state.discipline) {
        state.discipline?.let {
            name = TextFieldValue()
            onDisciplineCreated(it)
        }
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.form_discipline_create_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    BottomSheetForm(
        confirmText = { stringResource(R.string.form_discipline_create_confirm_text) },
        isVisible = modalOpen,
        isLoading = state.isLoading,
        onConfirm = {
            viewModel.createDiscipline(curriculumId, Discipline.CreateRequest(name.text))
        },
        titleText = { stringResource(R.string.form_discipline_create_title) },
        onClosed = onClosed,
    ) {
        Field(
            value = name,
            onValueChange = { name = it },
            label = { stringResource(R.string.form_discipline_create_name_label) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
