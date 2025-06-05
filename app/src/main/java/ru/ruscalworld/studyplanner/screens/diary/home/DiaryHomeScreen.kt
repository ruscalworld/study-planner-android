package ru.ruscalworld.studyplanner.screens.diary.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.AvailableDraftsNote
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.common.LoadingVisibility

@Composable
fun DiaryHomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDiscipline: (Long) -> Unit,
    navigateToTask: (Long, Long) -> Unit,
    navigateToDrafts: () -> Unit,
    scaffoldPadding: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.diary_home_unknown_error,
    )

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.diary_home_loading_title) },
            description = { stringResource(R.string.diary_home_loading_description) },
        )
    }

    LoadingVisibility(isLoading = state.isLoading) {
        CommonLayout(scaffoldPadding = scaffoldPadding) {
            state.drafts?.let {
                if (it.isNotEmpty()) AvailableDraftsNote(it.size, navigateToDrafts)
            }

            state.prioritizedTasks?.let {
                PrioritizedTasksBlock(it, navigateToTask)
            }

            state.disciplines?.let {
                DisciplinesBlock(it, navigateToDiscipline)
            }
        }
    }
}
