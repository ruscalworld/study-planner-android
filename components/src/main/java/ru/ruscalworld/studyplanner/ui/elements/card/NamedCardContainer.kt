package ru.ruscalworld.studyplanner.ui.elements.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.ui.elements.common.Headline

@Composable
fun NamedCardContainer(
    title: @Composable () -> String,
    description: (@Composable () -> String)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Headline(title = title, description = description, highlight = description != null)

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            content()
        }
    }
}
