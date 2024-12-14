package ru.ruscalworld.studyplanner.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.ui.elements.card.Surface
import ru.ruscalworld.studyplanner.ui.theme.SecondaryText

@Composable
fun AddLinkButton(
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.fa_plus_solid),
                contentDescription = null,
                tint = SecondaryText,
                modifier = Modifier.height(20.dp),
            )
        }
    }
}
