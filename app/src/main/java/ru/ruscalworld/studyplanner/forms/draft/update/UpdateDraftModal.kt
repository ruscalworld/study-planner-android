package ru.ruscalworld.studyplanner.forms.draft.update

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.core.model.Draft
import ru.ruscalworld.studyplanner.ui.elements.card.ConfirmationButton
import ru.ruscalworld.studyplanner.ui.elements.field.Field
import ru.ruscalworld.studyplanner.ui.elements.form.BottomSheetForm
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun UpdateDraftModal(
    viewModel: UpdateDraftModalViewModel = hiltViewModel(),
    draft: Draft?,
    modalOpen: Boolean,
    onClosed: () -> Unit = {},
    onDraftSaved: (Draft) -> Unit = {},
    onDraftDeleted: () -> Unit = {},
    snackbarHostState: SnackbarHostState,
) {
    val state by viewModel.uiState.collectAsState()
    var text by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(state.draft) {
        state.draft?.let {
            text = TextFieldValue(text = it.text)
            onDraftSaved(it)
        }
    }

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onDraftDeleted()
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.form_draft_update_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    BottomSheetForm(
        confirmText = { stringResource(R.string.form_draft_update_confirm_text) },
        isVisible = modalOpen,
        isLoading = state.isLoading,
        onConfirm = {
            if (draft == null)
                viewModel.createDraft(Draft.CreateRequest(text.text))
            else
                viewModel.updateDraft(draft.id, Draft.UpdateRequest(text.text))

            onClosed()
        },
        titleText = { stringResource(R.string.form_draft_update_title) },
        onClosed = onClosed,
    ) {
        Field(
            value = text,
            onValueChange = { text = it },
            label = { stringResource(R.string.form_draft_update_text_label) },
            lines = 5,
            modifier = Modifier.fillMaxWidth(),
        )

        if (draft != null) {
            ConfirmationButton(
                onConfirm = {
                    viewModel.deleteDraft(draft.id)
                    onClosed()
                },
                content = { stringResource(R.string.form_draft_update_delete_button) },
                confirmContent = { stringResource(R.string.form_draft_update_delete_confirm_button) },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.fa_trash_can_solid),
                        tint = PrimaryText,
                        contentDescription = null,
                    )
                }
            )
        }
    }
}
