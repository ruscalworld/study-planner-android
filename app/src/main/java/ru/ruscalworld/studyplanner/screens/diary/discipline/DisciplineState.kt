package ru.ruscalworld.studyplanner.screens.diary.discipline

import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.core.model.TaskProgress

data class DisciplineState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,

    val discipline: Discipline? = null,
    val taskGroups: List<Task.Group>? = null,
    val tasks: HashMap<Long, List<Pair<Task, TaskProgress>>>? = null,
    val links: List<Discipline.Link>? = null,
)
