package ru.ruscalworld.studyplanner.screens.diary.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.DisciplineCard
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.core.model.CompletionProgress
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer

@Composable
fun DiaryHomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDiscipline: (Long) -> Unit,
    navigateToTask: (Long, Long) -> Unit,
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

        return
    }

    CommonLayout {
        NamedCardContainer(
            title = { stringResource(R.string.diary_home_prioritized_tasks_title) },
            description = { stringResource(R.string.diary_home_prioritized_tasks_description) },
        ) {
//            TaskCard(
//                task = Task(1, "Example very long task name bla bla bla", "Sample description", null, 1, Task.Status.Available, 1, Date(System.currentTimeMillis() + 10 * 86400 * 1000)),
//                discipline = Discipline(1, "Sample discipline"),
//                navigateToTask = navigateToTask,
//            )
        }

        NamedCardContainer(
            title = { stringResource(R.string.diary_home_disciplines_title) },
            description = { stringResource(R.string.diary_home_disciplines_description) },
        ) {
            DisciplineCard(
                discipline = Discipline(1, "Sample very very long discipline name"),
                navigateTo = navigateToDiscipline,

                progress = CompletionProgress(
                    scope = CompletionProgress.DisciplineScope(discipline = Discipline(1, "Sample discipline")),
                    tasksTotal = 100,
                    tasksAvailable = 50,
                    tasksCompleted = 20,
                )
            )
        }
    }
}
