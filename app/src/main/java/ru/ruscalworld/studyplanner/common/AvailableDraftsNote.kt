package ru.ruscalworld.studyplanner.common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.ui.elements.card.CardButton
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun AvailableDraftsNote(draftCount: Int, onClick: () -> Unit) {
    CardButton(
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(R.drawable.fa_note_sticky_solid),
                tint = PrimaryText,
                contentDescription = null,
            )
        }
    ) {
        stringResource(
            R.string.common_drafts_available,
            LocalContext.current.resources.getQuantityString(
                R.plurals.common_drafts_available_count,
                draftCount,
                draftCount,
            ),
        )
    }
}
