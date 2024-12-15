package ru.ruscalworld.studyplanner.screens.diary.discipline

import androidx.compose.runtime.Composable
import ru.ruscalworld.studyplanner.adapters.TaskCard
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer

@Composable
fun TaskGroupContainer(
    taskGroup: Task.Group,
    tasks: List<Task>,
    navigateToTask: (Long) -> Unit,
) {
    NamedCardContainer(
        title = { taskGroup.name }
    ) {
        for (task in tasks) {
            TaskCard(
                task = task,
                navigateToTask = navigateToTask,
            )
        }
    }
}
