package ru.ruscalworld.studyplanner.screens.stats.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.LoadingScreen

@Composable
fun StatsHomeScreen(
    viewModel: StatsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.stats_home_loading_title) },
            description = { stringResource(R.string.stats_home_loading_description) },
        )
    }
}
