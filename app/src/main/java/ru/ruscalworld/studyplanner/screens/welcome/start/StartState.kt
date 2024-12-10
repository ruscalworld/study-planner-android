package ru.ruscalworld.studyplanner.screens.welcome.start

data class StartState(
    val errorMessage: Int? = null,
    val isLoading: Boolean = false,
    val successfulAuth: Boolean = false,
)
