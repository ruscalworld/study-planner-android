package ru.ruscalworld.studyplanner.screens.diary.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.Link
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.LinkRow
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

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

        return
    }

    CommonLayout(scaffoldPadding = scaffoldPadding) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            state.task?.let {
                Headline(
                    title = { it.name },
                    description = it.externalName?.let{{ it }},
                    highlight = true,
                )
            }

            state.links?.let {
                if (it.isEmpty()) return@Column

                LinkRow {
                    for (link in it) {
                        Link(link = link)
                    }
                }
            }
        }

        state.task?.description?.let {
            Text(it, style = AppTypography.bodyMedium)
        }

        state.progress?.let {
            TaskProgressRow(status = it.status)

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SuggestedTransitions(
                    status = it.status,
                    onChangeRequest = { status ->
                        viewModel.updateProgress(disciplineId, taskId, TaskProgress(status))
                    }
                )
            }
        }
    }
}
