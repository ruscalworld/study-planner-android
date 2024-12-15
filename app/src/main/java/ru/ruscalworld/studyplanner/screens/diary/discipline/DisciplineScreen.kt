package ru.ruscalworld.studyplanner.screens.diary.discipline

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.common.LoadingVisibility

@Composable
fun DisciplineScreen(
    viewModel: DisciplineViewModel = hiltViewModel(),
    disciplineId: Long,
    navigateToTask: (Long, Long) -> Unit,
    scaffoldPadding: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(disciplineId) {
        viewModel.load(disciplineId)
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.diary_discipline_unknown_error,
    )

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.diary_discipline_loading_title) },
            description = { stringResource(R.string.diary_discipline_loading_description) },
        )
    }

    LoadingVisibility(isLoading = state.isLoading) {
        CommonLayout(scaffoldPadding = scaffoldPadding) {
            DisciplineScreenContent(
                state = state,
                disciplineId = disciplineId,
                navigateToTask = navigateToTask,
            )
        }
    }
}
