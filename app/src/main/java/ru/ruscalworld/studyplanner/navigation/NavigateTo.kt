package ru.ruscalworld.studyplanner.navigation

fun interface NavigateTo<I> {
    fun navigate(to: EntityRouteKey<I>): () -> Unit
}
