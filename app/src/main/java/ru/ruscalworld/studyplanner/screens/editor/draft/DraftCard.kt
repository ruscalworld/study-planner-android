package ru.ruscalworld.studyplanner.screens.editor.draft

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.core.model.Draft
import ru.ruscalworld.studyplanner.ui.elements.card.Card
import ru.ruscalworld.studyplanner.ui.theme.AppTypography
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun DraftCard(
    draft: Draft,
    onEditRequest: () -> Unit,
) {
    Card(
        onClick = { onEditRequest() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    draft.text,
                    style = AppTypography.bodyMedium,
                )
                Text(
                    DateTimeFormatter
                        .ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .format(draft.createdAt.atZone(ZoneId.systemDefault())),
                    style = AppTypography.labelMedium,
                )
            }
        }
    }
}
