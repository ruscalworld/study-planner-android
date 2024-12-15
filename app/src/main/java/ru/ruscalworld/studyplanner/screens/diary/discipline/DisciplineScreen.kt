package ru.ruscalworld.studyplanner.screens.diary.discipline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.Link
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.LinkRow
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.ui.elements.common.Headline

@Composable
fun DisciplineScreen(
    viewModel: DisciplineViewModel = hiltViewModel(),
    disciplineId: Long,
    navigateToTask: (Long, Long) -> Unit,
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

        return
    }

    CommonLayout {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Headline(
                title = { state.discipline?.name?.toUpperCase(Locale.current) ?: "" },
                highlight = true
            )

            state.links?.let {
                if (it.isEmpty()) return@Column

                LinkRow {
                    for (link in it) {
                        Link(link = link)
                    }
                }
            }
        }

        state.taskGroups?.let {
            for (group in it) {
                val tasks = state.tasks
                val groupId = group.id

                if (tasks == null) continue

                TaskGroupContainer(
                    taskGroup = group,
                    tasks = tasks[groupId] ?: listOf(),
                    navigateToTask = { taskId -> navigateToTask(disciplineId, taskId) },
                )
            }
        }
    }
}
