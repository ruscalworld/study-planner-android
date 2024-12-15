package ru.ruscalworld.studyplanner.screens.welcome.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.common.LoadingVisibility

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
    }

    LoadingVisibility(isLoading = state.isInitialLoading) {
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
}
