package ru.ruscalworld.studyplanner.ui.elements.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.ruscalworld.studyplanner.ui.theme.CardBorder
import ru.ruscalworld.studyplanner.ui.theme.Red

@Composable
fun ConfirmationButton(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    content: @Composable () -> String,
    confirmContent: @Composable () -> String,
) {
    var requested by remember { mutableStateOf(false) }

    CardButton(
        onClick = {
            if (requested) onConfirm()
            else requested = true
        },
        content = if (requested) confirmContent else content,
        icon = icon,
        borderColor = if (requested) Red else CardBorder,
        modifier = modifier,
    )
}
