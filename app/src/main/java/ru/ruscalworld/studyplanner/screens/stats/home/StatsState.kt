package ru.ruscalworld.studyplanner.screens.stats.home

data class StatsState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)
