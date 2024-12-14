package ru.ruscalworld.studyplanner.screens.diary.task

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.elements.card.CardButton
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun SuggestedTransitions(
    status: TaskProgress.Status,
) {
    when (status) {
        TaskProgress.Status.NotStarted -> {
            MarkAsInProgressButton(onClick = {})
            MarkAsCompletedButton(onClick = {})
        }

        TaskProgress.Status.InProgress -> {
            MarkAsNeedsProtectionButton(onClick = {})
            MarkAsCompletedButton(onClick = {})
        }

        TaskProgress.Status.NeedsProtection -> {
            MarkAsInProgressButton(onClick = {})
            MarkAsCompletedButton(onClick = {})
        }

        TaskProgress.Status.Completed -> {}
    }
}

@Composable
fun MarkAsNotStartedButton(onClick: () -> Unit) {
    TransitionButton(
        onClick = onClick,
        icon = R.drawable.fa_xmark_solid,
        text = R.string.diary_task_transition_mark_as_not_started,
    )
}

@Composable
fun MarkAsInProgressButton(onClick: () -> Unit) {
    TransitionButton(
        onClick = onClick,
        icon = R.drawable.fa_hourglass_half_solid,
        text = R.string.diary_task_transition_mark_as_in_progress,
    )
}

@Composable
fun MarkAsNeedsProtectionButton(onClick: () -> Unit) {
    TransitionButton(
        onClick = onClick,
        icon = R.drawable.fa_umbrella_solid,
        text = R.string.diary_task_transition_mark_as_needs_protection,
    )
}

@Composable
fun MarkAsCompletedButton(onClick: () -> Unit) {
    TransitionButton(
        onClick = onClick,
        icon = R.drawable.fa_check_solid,
        text = R.string.diary_task_transition_mark_as_completed,
    )
}

@Composable
fun TransitionButton(
    onClick: () -> Unit,
    icon: Int,
    text: Int,
) {
    CardButton(
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = PrimaryText,
            )
        }
    ) {
        stringResource(text)
    }
}
