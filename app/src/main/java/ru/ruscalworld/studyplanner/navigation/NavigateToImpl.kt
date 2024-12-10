package ru.ruscalworld.studyplanner.navigation

import androidx.navigation.NavController

internal class NavigateToImpl<I>(private val navController: NavController, private val moduleName: String): NavigateTo<I> {
    override fun navigate(to: EntityRouteKey<I>): () -> Unit = {
        navController.navigate("${moduleName}/${to.entityPathName}/${to.entityId}")
    }
}