package ru.ruscalworld.studyplanner.screens.diary.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.DisciplineCard
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer

@Composable
fun DisciplinesBlock(
    disciplines: List<DisciplineProgress>,
    navigateToDiscipline: (Long) -> Unit,
) {
    NamedCardContainer(
        title = { stringResource(R.string.diary_home_disciplines_title) },
        description = { stringResource(R.string.diary_home_disciplines_description) },
    ) {
        for (discipline in disciplines) {
            DisciplineCard(
                discipline = discipline.discipline,
                navigateTo = navigateToDiscipline,
                progress = discipline.stats,
            )
        }
    }
}
