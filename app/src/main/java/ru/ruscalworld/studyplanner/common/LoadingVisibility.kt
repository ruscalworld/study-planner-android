package ru.ruscalworld.studyplanner.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable

@Composable
fun LoadingVisibility(
    isLoading: Boolean,
    contents: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = !isLoading,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300))
    ) {
        contents()
    }
}
