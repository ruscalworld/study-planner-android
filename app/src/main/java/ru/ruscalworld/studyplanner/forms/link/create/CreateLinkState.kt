package ru.ruscalworld.studyplanner.forms.link.create

import ru.ruscalworld.studyplanner.core.model.EntityLink

data class CreateLinkState<L: EntityLink>(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val link: L? = null,
)
