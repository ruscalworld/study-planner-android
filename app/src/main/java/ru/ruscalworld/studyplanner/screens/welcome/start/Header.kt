package ru.ruscalworld.studyplanner.screens.welcome.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun Header() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("animations/waving_hand.json"))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(64.dp)
    ) {
        LottieAnimation(composition, modifier = Modifier.height(128.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                stringResource(R.string.start_welcome_title),
                style = AppTypography.headlineMedium,
                textAlign = TextAlign.Center,
            )

            Text(
                stringResource(R.string.start_welcome_text),
                style = AppTypography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}
