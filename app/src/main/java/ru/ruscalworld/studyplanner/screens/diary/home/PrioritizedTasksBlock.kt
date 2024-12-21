package ru.ruscalworld.studyplanner.screens.diary.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.TaskCard
import ru.ruscalworld.studyplanner.core.model.DisciplineTask
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer

@Composable
fun PrioritizedTasksBlock(
    tasks: List<Pair<DisciplineTask, TaskProgress>>,
    navigateToTask: (Long, Long) -> Unit,
) {
    NamedCardContainer(
        title = { stringResource(R.string.diary_home_prioritized_tasks_title) },
        description = { stringResource(R.string.diary_home_prioritized_tasks_description) },
    ) {
        for (task in tasks) {
            TaskCard(
                task = task.first,
                progress = task.second,
                navigateToTask = navigateToTask,
            )
        }
    }
}
