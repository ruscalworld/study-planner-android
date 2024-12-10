package ru.ruscalworld.studyplanner.forms.curriculum.create

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
import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.ui.elements.field.Field
import ru.ruscalworld.studyplanner.ui.elements.form.BottomSheetForm
import ru.ruscalworld.studyplanner.common.ExceptionHandler

@Composable
fun CreateCurriculumModal(
    viewModel: CreateCurriculumModalViewModel = hiltViewModel(),
    modalOpen: Boolean,
    onClosed: () -> Unit,
    onCurriculumCreated: (Curriculum) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val state by viewModel.uiState.collectAsState()
    var name by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(state.curriculum) {
        state.curriculum?.let { onCurriculumCreated(it) }
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.form_curriculum_create_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    BottomSheetForm(
        confirmText = { stringResource(R.string.form_curriculum_create_confirm_text) },
        isVisible = modalOpen,
        isLoading = state.isLoading,
        onConfirm = {
            viewModel.createCurriculum(CreateCurriculumRequest(name.text))
        },
        titleText = { stringResource(R.string.form_curriculum_create_title) },
        onClosed = onClosed,
    ) {
        Field(
            value = name,
            onValueChange = { name = it },
            label = { stringResource(R.string.form_curriculum_create_name_label) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

data class CreateCurriculumRequest(
    val name: String,
)
