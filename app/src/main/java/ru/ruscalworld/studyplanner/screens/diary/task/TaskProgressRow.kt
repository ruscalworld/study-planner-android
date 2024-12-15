package ru.ruscalworld.studyplanner.screens.diary.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.theme.AppTypography
import ru.ruscalworld.studyplanner.ui.theme.Blue
import ru.ruscalworld.studyplanner.ui.theme.Green
import ru.ruscalworld.studyplanner.ui.theme.Red
import ru.ruscalworld.studyplanner.ui.theme.Yellow

@Composable
fun TaskProgressRow(
    status: TaskProgress.Status,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center,
        ) {
            ProgressIcon(status)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(2f),
        ) {
            Text(
                stringResource(R.string.diary_task_status_title),
                style = AppTypography.labelMedium,
            )

            Text(
                stringResource(when (status) {
                    TaskProgress.Status.NotStarted -> R.string.diary_task_status_not_started
                    TaskProgress.Status.InProgress -> R.string.diary_task_status_in_progress
                    TaskProgress.Status.NeedsProtection -> R.string.diary_task_status_needs_protection
                    TaskProgress.Status.Completed -> R.string.diary_task_status_completed
                }),

                style = AppTypography.displayLarge,
            )
        }
    }
}

@Composable
fun ProgressIcon(status: TaskProgress.Status) {
    Icon(
        painter = when (status) {
            TaskProgress.Status.NotStarted -> painterResource(R.drawable.fa_xmark_solid)
            TaskProgress.Status.InProgress -> painterResource(R.drawable.fa_hourglass_half_solid)
            TaskProgress.Status.NeedsProtection -> painterResource(R.drawable.fa_umbrella_solid)
            TaskProgress.Status.Completed -> painterResource(R.drawable.fa_check_solid)
        },

        contentDescription = null,

        tint = when (status) {
            TaskProgress.Status.NotStarted -> Red
            TaskProgress.Status.InProgress -> Blue
            TaskProgress.Status.NeedsProtection -> Yellow
            TaskProgress.Status.Completed -> Green
        },

        modifier = Modifier.size(32.dp)
    )
}
