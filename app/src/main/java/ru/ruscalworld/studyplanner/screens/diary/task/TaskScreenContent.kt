package ru.ruscalworld.studyplanner.screens.diary.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.adapters.DeadlineRow
import ru.ruscalworld.studyplanner.adapters.EstimatedTimeRow
import ru.ruscalworld.studyplanner.adapters.Link
import ru.ruscalworld.studyplanner.common.LinkRow
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun TaskScreenContent(
    state: TaskState,
    onStatusTransition: (TaskProgress.Status) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        state.task?.let {
            Headline(
                title = { it.name },
                description = it.externalName?.let{{ it }},
                highlight = true,
            )
        }

        state.links?.let {
            if (it.isEmpty()) return@Column

            LinkRow {
                for (link in it) {
                    Link(link = link)
                }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        state.task?.deadline?.let { DeadlineRow(it) }
        state.task?.let { EstimatedTimeRow(it.difficulty) }
    }

    state.task?.description?.let {
        Text(it, style = AppTypography.bodyMedium)
    }

    state.progress?.let {
        TaskProgressRow(status = it.status)

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SuggestedTransitions(
                status = it.status,
                onChangeRequest = onStatusTransition,
            )
        }
    }
}
