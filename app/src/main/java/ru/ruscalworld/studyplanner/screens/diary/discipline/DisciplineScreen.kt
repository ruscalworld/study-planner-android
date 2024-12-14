package ru.ruscalworld.studyplanner.screens.diary.discipline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.Link
import ru.ruscalworld.studyplanner.common.LinkRow
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Task
import ru.ruscalworld.studyplanner.ui.elements.common.Headline

@Composable
fun DisciplineScreen(
    viewModel: DisciplineViewModel = hiltViewModel(),
    disciplineId: Long,
    navigateToTask: (Long, Long) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.diary_discipline_loading_title) },
            description = { stringResource(R.string.diary_discipline_loading_description) },
        )
    }

    CommonLayout {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Headline(title = { "Sample long discipline name".toUpperCase(Locale.current) }, highlight = true)

            LinkRow {
                Link(Discipline.Link(1, "Google", "https://google.com"))
                Link(Discipline.Link(1, "Yandex", "https://yandex.ru"))
                Link(Discipline.Link(1, "Rambler", "https://rambler.ru"))
            }
        }

        TaskGroupContainer(
            taskGroup = Task.Group(1, "Sample group"),
            tasks = listOf(),
            navigateToTask = { taskId -> navigateToTask(disciplineId, taskId) },
        )

        TaskGroupContainer(
            taskGroup = Task.Group(1, "Sample another"),
            tasks = listOf(),
            navigateToTask = { taskId -> navigateToTask(disciplineId, taskId) },
        )
    }
}
