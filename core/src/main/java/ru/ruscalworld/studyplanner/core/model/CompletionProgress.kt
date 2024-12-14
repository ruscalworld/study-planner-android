package ru.ruscalworld.studyplanner.core.model

data class CompletionProgress<S : CompletionProgress.Scope>(
    val scope: S,
    val tasksTotal: Int,
    val tasksAvailable: Int,
    val tasksCompleted: Int,
) {
    val completedOfTotal: Double = tasksCompleted.toDouble() / tasksTotal.toDouble()
    val completedOfAvailable: Double = tasksCompleted.toDouble() / tasksAvailable.toDouble()

    interface Scope

    data class DisciplineScope(val discipline: Discipline) : Scope
}
