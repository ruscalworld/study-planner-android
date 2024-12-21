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
    onChangeRequest: (TaskProgress.Status) -> Unit,
) {
    when (status) {
        TaskProgress.Status.NotStarted -> {
            MarkAsInProgressButton(onChangeRequest)
            MarkAsCompletedButton(onChangeRequest)
        }

        TaskProgress.Status.InProgress -> {
            MarkAsNeedsProtectionButton(onChangeRequest)
            MarkAsCompletedButton(onChangeRequest)
        }

        TaskProgress.Status.NeedsProtection -> {
            MarkAsInProgressButton(onChangeRequest)
            MarkAsCompletedButton(onChangeRequest)
        }

        TaskProgress.Status.Completed -> {
            MarkAsNotStartedButton(onChangeRequest)
        }
    }
}

@Composable
fun MarkAsNotStartedButton(onChangeRequest: (TaskProgress.Status) -> Unit) {
    TransitionButton(
        onClick = { onChangeRequest(TaskProgress.Status.NotStarted) },
        icon = R.drawable.fa_xmark_solid,
        text = R.string.diary_task_transition_mark_as_not_started,
    )
}

@Composable
fun MarkAsInProgressButton(onChangeRequest: (TaskProgress.Status) -> Unit) {
    TransitionButton(
        onClick = { onChangeRequest(TaskProgress.Status.InProgress) },
        icon = R.drawable.fa_rocket_solid,
        text = R.string.diary_task_transition_mark_as_in_progress,
    )
}

@Composable
fun MarkAsNeedsProtectionButton(onChangeRequest: (TaskProgress.Status) -> Unit) {
    TransitionButton(
        onClick = { onChangeRequest(TaskProgress.Status.NeedsProtection) },
        icon = R.drawable.fa_umbrella_solid,
        text = R.string.diary_task_transition_mark_as_needs_protection,
    )
}

@Composable
fun MarkAsCompletedButton(onChangeRequest: (TaskProgress.Status) -> Unit) {
    TransitionButton(
        onClick = { onChangeRequest(TaskProgress.Status.Completed) },
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
