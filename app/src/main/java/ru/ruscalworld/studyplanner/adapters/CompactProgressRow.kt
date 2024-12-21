package ru.ruscalworld.studyplanner.adapters

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.IconRow
import ru.ruscalworld.studyplanner.common.ProgressIcon
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun ProgressRow(
    progress: TaskProgress,
) {
    IconRow(
        icon = {
            ProgressIcon(progress.status)
        }
    ) {
        Text(
            stringResource(when (progress.status) {
                TaskProgress.Status.NotStarted -> R.string.diary_task_status_not_started
                TaskProgress.Status.InProgress -> R.string.diary_task_status_in_progress
                TaskProgress.Status.NeedsProtection -> R.string.diary_task_status_needs_protection
                TaskProgress.Status.Completed -> R.string.diary_task_status_completed
            }),

            style = AppTypography.bodySmall,
        )
    }
}
