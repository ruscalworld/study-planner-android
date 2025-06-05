package ru.ruscalworld.studyplanner.screens.editor.draft

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.Draft
import ru.ruscalworld.studyplanner.ui.elements.card.CardButton
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun DraftListScreenContent(
    viewModel: DraftListViewModel = hiltViewModel(),
    onEditRequest: (Draft) -> Unit,
    onCreateRequest: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    Headline(
        title = { stringResource(R.string.editor_drafts_title).toUpperCase(Locale.current) },
        highlight = true,
    )

    state.drafts?.let {
        for (draft in it) {
            DraftCard(
                draft = draft,
                onEditRequest = { onEditRequest(draft) },
            )
        }
    }

    CardButton(
        onClick = { onCreateRequest() },
        icon = {
            Icon(
                painter = painterResource(R.drawable.fa_plus_solid),
                tint = PrimaryText,
                contentDescription = null,
            )
        }
    ) {
        stringResource(R.string.editor_drafts_create_button)
    }
}
