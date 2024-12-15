package ru.ruscalworld.studyplanner.screens.diary.task

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
import ru.ruscalworld.studyplanner.core.model.TaskProgress

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    disciplineId: Long,
    taskId: Long,
    scaffoldPadding: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load(disciplineId, taskId)
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.diary_task_unknown_error,
    )

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.diary_task_loading_title) },
            description = { stringResource(R.string.diary_task_loading_description) },
        )
    }

    LoadingVisibility(isLoading = state.isLoading) {
        CommonLayout(scaffoldPadding = scaffoldPadding) {
            TaskScreenContent(
                state = state,
                onStatusTransition = { status ->
                    viewModel.updateProgress(disciplineId, taskId, TaskProgress(status))
                }
            )
        }
    }
}
