package ru.ruscalworld.studyplanner.ui.elements.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun NamedCardContainer(
    title: @Composable () -> String,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(title(), style = AppTypography.titleMedium)

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            content()
        }
    }
}
