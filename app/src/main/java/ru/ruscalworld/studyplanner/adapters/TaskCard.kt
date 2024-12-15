package ru.ruscalworld.studyplanner.adapters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.DisciplineTask
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.ui.elements.card.Card
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun TaskCard(
    task: Task,
    navigateToTask: (Long) -> Unit,
) {
    Card(
        onClick = { navigateToTask(task.id) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(task.name, style = AppTypography.displayMedium)
                task.externalName?.let { Text(it, style = AppTypography.labelMedium) }
            }

            task.deadline?.let { DeadlineRow(it) }
            EstimatedTimeRow(task.difficulty)
        }
    }
}

@Composable
fun TaskCard(
    task: DisciplineTask,
    navigateToTask: (Long, Long) -> Unit,
) {
    Card(
        onClick = { navigateToTask(task.discipline.id, task.id) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    task.discipline.name,
                    style = AppTypography.displayMedium,
                )
                Text(
                    task.name + task.externalName?.let { " " + stringResource(R.string.quoted_string, it) },
                    style = AppTypography.labelMedium,
                )
            }

            task.deadline?.let { DeadlineRow(it) }
            EstimatedTimeRow(task.difficulty)
        }
    }
}
