package ru.ruscalworld.studyplanner.navigation

import android.util.Log
import androidx.navigation.NavController

internal class NavigateToImpl<I>(private val navController: NavController, private val moduleName: String): NavigateTo<I> {
    override fun navigate(to: EntityRouteKey<I>): () -> Unit = {
        Log.d("AAA", to.entityPathName + to.entityId) // TODO
        navController.navigate("${moduleName}/${to.entityPathName}/${to.entityId}")
    }
}