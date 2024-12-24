package ru.ruscalworld.studyplanner.screens.diary.discipline

import androidx.compose.runtime.Composable
import ru.ruscalworld.studyplanner.adapters.TaskCard
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer

@Composable
fun TaskGroupContainer(
    taskGroup: Task.Group,
    tasks: List<Pair<Task, TaskProgress>>,
    navigateToTask: (Long) -> Unit,
) {
    NamedCardContainer(
        title = { taskGroup.name }
    ) {
        for (task in tasks.sortedBy { it.first.id }) {
            TaskCard(
                task = task.first,
                progress = task.second,
                navigateToTask = navigateToTask,
            )
        }
    }
}
