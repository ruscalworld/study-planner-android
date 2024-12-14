package ru.ruscalworld.studyplanner.screens.welcome.start

data class StartState(
    val isInitialLoading: Boolean = true,
    val errorMessage: Int? = null,
    val isLoading: Boolean = false,
    val successfulAuth: Boolean = false,
    val isCurriculumPicked: Boolean = false,
)
