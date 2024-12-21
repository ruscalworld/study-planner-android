package ru.ruscalworld.studyplanner.screens.editor.task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
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
import ru.ruscalworld.studyplanner.common.TextFieldRow
import ru.ruscalworld.studyplanner.ui.elements.card.ConfirmationButton
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.elements.field.DateField
import ru.ruscalworld.studyplanner.ui.elements.field.Field
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun TaskEditorScreenContent(
    viewModel: TaskEditorViewModel = hiltViewModel(),
    onCreateLinkRequest: () -> Unit,
    onDeleteRequest: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    val name by viewModel.name.collectAsState()
    val externalName by viewModel.externalName.collectAsState()
    val description by viewModel.description.collectAsState()
    val difficulty by viewModel.difficulty.collectAsState()
    val deadline by viewModel.deadline.collectAsState()

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

        AddLinkButton(onClick = { onCreateLinkRequest() })
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
                initialValue = deadline,
                modifier = Modifier.fillMaxWidth(),
                onDatePicked = { viewModel.onDeadlineChanged(it) },
                confirmText = R.string.editor_task_deadline_pick_confirm,
                dismissText = R.string.editor_task_deadline_pick_dismiss,
            )
        }
    }

    ConfirmationButton(
        onConfirm = onDeleteRequest,
        content = { stringResource(R.string.editor_task_delete_button) },
        confirmContent = { stringResource(R.string.editor_task_delete_confirm_button) },
        icon = {
            Icon(
                painter = painterResource(R.drawable.fa_trash_can_solid),
                tint = PrimaryText,
                contentDescription = null,
            )
        }
    )
}
