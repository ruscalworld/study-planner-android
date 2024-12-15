package ru.ruscalworld.studyplanner.screens.diary.discipline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.adapters.Link
import ru.ruscalworld.studyplanner.common.LinkRow
import ru.ruscalworld.studyplanner.ui.elements.common.Headline

@Composable
fun DisciplineScreenContent(
    state: DisciplineState,
    disciplineId: Long,
    navigateToTask: (Long, Long) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Headline(
            title = { state.discipline?.name?.toUpperCase(Locale.current) ?: "" },
            highlight = true
        )

        state.links?.let {
            if (it.isEmpty()) return@Column

            LinkRow {
                for (link in it) {
                    Link(link = link)
                }
            }
        }
    }

    state.taskGroups?.let {
        for (group in it) {
            val tasks = state.tasks
            val groupId = group.id

            if (tasks == null) continue

            TaskGroupContainer(
                taskGroup = group,
                tasks = tasks[groupId] ?: listOf(),
                navigateToTask = { taskId -> navigateToTask(disciplineId, taskId) },
            )
        }
    }
}
