package ru.ruscalworld.studyplanner.screens.diary.discipline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.LoadingScreen

@Composable
fun DisciplineScreen(
    viewModel: DisciplineViewModel = hiltViewModel(),
    disciplineId: Long,
) {
    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.diary_discipline_loading_title) },
            description = { stringResource(R.string.diary_discipline_loading_description) },
        )
    }
}
