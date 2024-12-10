package ru.ruscalworld.studyplanner.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    background = Background,
    onBackground = PrimaryText,

    surface = PrimaryBackground,
    onSurface = SurfaceText,

    primary = PrimaryColor,
    onPrimary = PrimaryText,

    secondary = CardBackground,
    onSecondary = PrimaryText,

    outline = CardBorder,
    primaryContainer = CardBackground,
)

@Composable
fun StudyPlannerTheme(
    content: @Composable () -> Unit
) {
    val window = (LocalView.current.context as Activity).window

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        content = content,
    )
    SideEffect {
        window.statusBarColor = PrimaryBackground.toArgb()
    }
}
