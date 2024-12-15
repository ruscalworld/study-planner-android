package ru.ruscalworld.studyplanner.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun LoadingScreen(title: @Composable () -> String, description: @Composable () -> String) {
    var contentsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        contentsVisible = true
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AnimatedVisibility(
            visible = contentsVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 300)),
            exit = fadeOut(animationSpec = tween(durationMillis = 300))
        ) {
            Contents(title = title, description = description)
        }
    }
}

@Composable
fun Contents(title: @Composable () -> String, description: @Composable () -> String) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("animations/hourglass.json"))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(64.dp),
        modifier = Modifier.safeGesturesPadding(),
    ) {
        LottieAnimation(composition, modifier = Modifier.height(128.dp), iterations = 3)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                title(),
                style = AppTypography.headlineMedium,
                textAlign = TextAlign.Center,
            )

            Text(
                description(),
                style = AppTypography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}
