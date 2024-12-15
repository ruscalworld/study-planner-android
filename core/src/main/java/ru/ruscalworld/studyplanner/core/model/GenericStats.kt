package ru.ruscalworld.studyplanner.core.model

data class GenericStats(
    val completedTasks: Int,
    val inProgressTasks: Int,
    val goalTasks: Int,
    val availableTasks: Int,
    val totalTasks: Int,
) {
    val completedOfTotal: Double = if (totalTasks == 0) 0.0
        else completedTasks.toDouble() / totalTasks.toDouble()

    val completedOfAvailable: Double = if (availableTasks == 0) 0.0
        else completedTasks.toDouble() / availableTasks.toDouble()
}
