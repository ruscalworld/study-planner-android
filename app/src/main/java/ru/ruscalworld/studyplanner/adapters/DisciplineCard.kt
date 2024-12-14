package ru.ruscalworld.studyplanner.adapters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.core.model.CompletionProgress
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.navigation.EntityRouteKey
import ru.ruscalworld.studyplanner.navigation.NavigateTo
import ru.ruscalworld.studyplanner.ui.elements.card.Card
import ru.ruscalworld.studyplanner.ui.theme.AppTypography
import ru.ruscalworld.studyplanner.ui.theme.PrimaryColor

@Composable
fun DisciplineCard(
    discipline: Discipline,
    navigateTo: (Long) -> Unit,
    progress: CompletionProgress<CompletionProgress.DisciplineScope>? = null,
) {
    Card(
        onClick = { navigateTo(discipline.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    discipline.name,
                    style = AppTypography.displayMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    "Sample Text",
                    style = AppTypography.labelMedium,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            progress?.let {
                Box(
                    modifier = Modifier.size(48.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        progress = { it.completedOfTotal.toFloat() },
                        modifier = Modifier.fillMaxSize(),
                    )

                    Text(
                        "${(it.completedOfTotal * 100).toInt()}%",
                        style = AppTypography.displaySmall,
                        fontSize = TextUnit(12f, TextUnitType.Sp),
                        color = PrimaryColor,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}
