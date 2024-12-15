package ru.ruscalworld.studyplanner.screens.editor.discipline

import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Task

data class DisciplineEditorState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,

    val curriculumId: Long? = null,
    val discipline: Discipline? = null,
    val taskGroups: List<Task.Group>? = null,
    val tasks: HashMap<Long, List<Task>>? = null,
    val links: List<Discipline.Link>? = null,
)
