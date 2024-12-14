package ru.ruscalworld.studyplanner.ui.elements.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.ui.theme.CardBackground
import ru.ruscalworld.studyplanner.ui.theme.CardBorder

@Composable
fun Surface(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, CardBorder),
        color = CardBackground,
        onClick = onClick,
    ) {
        content()
    }
}

@Composable
fun Surface(
    content: @Composable () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, CardBorder),
        color = CardBackground,
    ) {
        content()
    }
}
