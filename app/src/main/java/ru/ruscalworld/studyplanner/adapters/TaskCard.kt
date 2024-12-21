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
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.elements.card.Card
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun TaskCard(
    task: Task,
    progress: TaskProgress,
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

            if (progress.status != TaskProgress.Status.Completed) {
                task.deadline?.let { DeadlineRow(it) }
            }

            EstimatedTimeRow(task.difficulty)
            ProgressRow(progress)
        }
    }
}

@Composable
fun TaskCard(
    task: DisciplineTask,
    progress: TaskProgress,
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
                    text = task.externalName?.let {
                        task.name + " " + stringResource(R.string.quoted_string, it)
                    } ?: task.name,

                    style = AppTypography.labelMedium,
                )
            }

            if (progress.status != TaskProgress.Status.Completed) {
                task.deadline?.let { DeadlineRow(it) }
            }

            EstimatedTimeRow(task.difficulty)
            ProgressRow(progress)
        }
    }
}
