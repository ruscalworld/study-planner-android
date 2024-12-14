package ru.ruscalworld.studyplanner.screens.editor.discipline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.R
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
                Text(
                    task.name,
                    style = AppTypography.displayMedium,
                )
                Text(
                    task.externalName ?: stringResource(R.string.editor_discipline_task_no_description),
                    style = AppTypography.labelMedium,
                )
            }
        }
    }
}
