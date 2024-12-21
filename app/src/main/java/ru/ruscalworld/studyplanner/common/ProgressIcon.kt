package ru.ruscalworld.studyplanner.common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.theme.Blue
import ru.ruscalworld.studyplanner.ui.theme.Green
import ru.ruscalworld.studyplanner.ui.theme.Red
import ru.ruscalworld.studyplanner.ui.theme.Yellow


@Composable
fun ProgressIcon(status: TaskProgress.Status, modifier: Modifier = Modifier) {
    Icon(
        painter = when (status) {
            TaskProgress.Status.NotStarted -> painterResource(R.drawable.fa_xmark_solid)
            TaskProgress.Status.InProgress -> painterResource(R.drawable.fa_rocket_solid)
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

        modifier = modifier,
    )
}
