package ru.ruscalworld.studyplanner.ui.elements.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Card(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(
        onClick = onClick,
    ) {
        CardContent { content() }
    }
}

@Composable
fun Card(
    content: @Composable () -> Unit,
) {
    Surface {
        CardContent { content() }
    }
}

@Composable
fun CardContent(
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        content()
    }
}
