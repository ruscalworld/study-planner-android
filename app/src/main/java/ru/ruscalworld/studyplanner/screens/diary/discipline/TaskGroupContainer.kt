package ru.ruscalworld.studyplanner.screens.diary.discipline

import androidx.compose.runtime.Composable
import ru.ruscalworld.studyplanner.adapters.TaskCard
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer
import java.util.Date

@Composable
fun TaskGroupContainer(
    taskGroup: Task.Group,
    tasks: List<Task>,
    navigateToTask: (Long) -> Unit,
) {
    NamedCardContainer(
        title = { taskGroup.name }
    ) {
        TaskCard(
            task = Task(1, "Example very long task name bla bla bla", "Sample description", null, 1, Task.Status.Available, 1, Date(System.currentTimeMillis() + 10 * 86400 * 1000)),
            navigateToTask = navigateToTask,
        )
        TaskCard(
            task = Task(1, "Example very long task name bla bla bla", "Sample description", null, 1, Task.Status.Available, 1, Date(System.currentTimeMillis() + 10 * 86400 * 1000)),
            navigateToTask = navigateToTask,
        )
        TaskCard(
            task = Task(1, "Example very long task name bla bla bla", "Sample description", null, 1, Task.Status.Available, 1, Date(System.currentTimeMillis() + 10 * 86400 * 1000)),
            navigateToTask = navigateToTask,
        )
    }
}
