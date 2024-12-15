package ru.ruscalworld.studyplanner.screens.diary.home

import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.GenericStats

data class DisciplineProgress(
    val discipline: Discipline,
    val stats: GenericStats,
)
