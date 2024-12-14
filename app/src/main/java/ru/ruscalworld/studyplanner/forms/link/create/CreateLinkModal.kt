package ru.ruscalworld.studyplanner.forms.link.create

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.ui.elements.field.Field
import ru.ruscalworld.studyplanner.ui.elements.form.BottomSheetForm
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.core.model.EntityLink

@Composable
fun <L: EntityLink>CreateLinkModal(
    viewModel: CreateLinkModalViewModel<L>,
    modalOpen: Boolean,
    linkFactory: suspend (CreateLinkRequest) -> L,
    onClosed: () -> Unit,
    onLinkCreated: (L) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val state by viewModel.uiState.collectAsState()
    var name by remember { mutableStateOf(TextFieldValue()) }
    var url by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(state.link) {
        state.link?.let {
            onLinkCreated(it)
            onClosed()

            name = TextFieldValue()
            url = TextFieldValue()
        }
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.form_link_create_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    BottomSheetForm(
        confirmText = { stringResource(R.string.form_link_create_confirm_text) },
        isVisible = modalOpen,
        isLoading = state.isLoading,
        onConfirm = {
            viewModel.createLink(CreateLinkRequest(name.text, url.text), linkFactory)
        },
        titleText = { stringResource(R.string.form_link_create_title) },
        onClosed = onClosed,
    ) {
        Field(
            value = name,
            onValueChange = { name = it },
            label = { stringResource(R.string.form_link_create_name_label) },
            modifier = Modifier.fillMaxWidth(),
        )

        Field(
            value = url,
            onValueChange = { url = it },
            label = { stringResource(R.string.form_link_create_url_label) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                autoCorrectEnabled = false,
                capitalization = KeyboardCapitalization.None,
            ),
        )
    }
}

data class CreateLinkRequest(
    val name: String,
    val url: String,
)
