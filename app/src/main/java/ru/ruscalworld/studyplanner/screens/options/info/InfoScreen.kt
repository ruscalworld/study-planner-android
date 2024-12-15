package ru.ruscalworld.studyplanner.screens.options.info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.LoadingScreen

@Composable
fun InfoScreen(
    viewModel: InfoViewModel = hiltViewModel(),
    navigateToStart: () -> Unit,
    navigateToPickCurriculum: () -> Unit,
    navigateToHome: () -> Unit,
    scaffoldPadding: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    LaunchedEffect(state.signedOut) {
        if (state.signedOut) navigateToStart()
    }

    LaunchedEffect(state.activeCurriculumChanged) {
        if (state.activeCurriculumChanged) navigateToHome()
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.options_info_unknown_error,
    )

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.diary_home_loading_title) },
            description = { stringResource(R.string.diary_home_loading_description) },
        )

        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        Header()
        Options(navigateToPickCurriculum = navigateToPickCurriculum)
        Box(modifier = Modifier.padding(scaffoldPadding))
    }
}
