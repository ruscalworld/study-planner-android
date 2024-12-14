package ru.ruscalworld.studyplanner.adapters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.ui.elements.card.Card
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun CurriculumCard(
    curriculum: Curriculum,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                curriculum.name,
                style = AppTypography.displayMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                stringResource(R.string.common_curriculum_card_semester, curriculum.semester),
                style = AppTypography.labelMedium,
            )
        }
    }
}
