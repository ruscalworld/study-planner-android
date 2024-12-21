package ru.ruscalworld.studyplanner.ui.elements.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.ui.theme.AppTypography
import ru.ruscalworld.studyplanner.ui.theme.PrimaryColor

@Composable
fun Headline(
    title: @Composable () -> String,
    description: (@Composable () -> String)? = null,
    highlight: Boolean = false,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            title(),
            style = if (highlight) AppTypography.titleLarge else AppTypography.titleMedium,
            color = if (highlight) PrimaryColor else Color.Unspecified,
        )

        description?.let { Text(it(), style = AppTypography.labelMedium) }
    }
}
