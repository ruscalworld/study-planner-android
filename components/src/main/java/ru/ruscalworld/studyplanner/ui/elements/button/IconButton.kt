package ru.ruscalworld.studyplanner.ui.elements.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.ui.theme.CardBackground
import ru.ruscalworld.studyplanner.ui.theme.CardBorder

@Composable
fun IconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    OutlinedIconButton(
        modifier = Modifier.size(48.dp),
        border = BorderStroke(2.dp, CardBorder),
        colors = IconButtonDefaults.outlinedIconButtonColors(containerColor = CardBackground),
        onClick = onClick,
    ) {
        Box(modifier = Modifier.size(20.dp)) {
            icon()
        }
    }
}
