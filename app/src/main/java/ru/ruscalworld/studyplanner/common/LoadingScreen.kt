package ru.ruscalworld.studyplanner.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun LoadingScreen(title: @Composable () -> String, description: @Composable () -> String) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Contents(title = title, description = description)
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
