package ru.ruscalworld.studyplanner.screens.welcome.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.ui.elements.button.Button
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun StartScreen(
    viewModel: StartViewModel = hiltViewModel(),
    navigateToPickCurriculum: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage = state.errorMessage?.let { stringResource(it) }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { snackbarHostState.showSnackbar(errorMessage) }
    }

    LaunchedEffect(state.successfulAuth, state.isCurriculumPicked) {
        if (state.successfulAuth && state.isCurriculumPicked) {
            navigateToHome()
            return@LaunchedEffect
        }

        if (state.successfulAuth) {
            navigateToPickCurriculum()
            return@LaunchedEffect
        }
    }

    SnackbarHost(hostState = snackbarHostState)

    if (state.isInitialLoading) {
        LoadingScreen(
            title = { stringResource(R.string.start_welcome_loading_title) },
            description = { stringResource(R.string.start_welcome_loading_description) },
        )

        return
    }

    Column(
        modifier = Modifier
            .safeGesturesPadding()
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        ActionArea(isLoading = state.isLoading, authenticate = viewModel::signIn)
    }
}

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

@Composable
fun ActionArea(isLoading: Boolean, authenticate: () -> Unit) {
    Button(isLoading = isLoading, onClick = authenticate) {
        stringResource(R.string.start_welcome_authenticate)
    }
}
