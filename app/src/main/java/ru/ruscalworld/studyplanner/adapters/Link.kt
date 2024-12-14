package ru.ruscalworld.studyplanner.adapters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.EntityLink
import ru.ruscalworld.studyplanner.ui.elements.card.Surface
import ru.ruscalworld.studyplanner.ui.theme.AppTypography
import ru.ruscalworld.studyplanner.ui.theme.SecondaryText

@Composable
fun Link(
    link: EntityLink,
) {
    Surface {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.fa_link_solid),
                contentDescription = null,
                tint = SecondaryText,
                modifier = Modifier.height(20.dp),
            )

            Text(
                link.name.toUpperCase(Locale.current),
                style = AppTypography.displaySmall,
                color = SecondaryText,
            )
        }
    }
}
