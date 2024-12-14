package ru.ruscalworld.studyplanner.screens.diary.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.Link
import ru.ruscalworld.studyplanner.common.LinkRow
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.TaskProgress
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    disciplineId: Long,
    taskId: Long,
) {
    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.diary_task_loading_title) },
            description = { stringResource(R.string.diary_task_loading_description) },
        )
    }

    CommonLayout {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Headline(
                title = { "Sample task name" },
                description = { "External task name" },
                highlight = true,
            )

            LinkRow {
                Link(Discipline.Link(1, "Google", "https://google.com"))
                Link(Discipline.Link(1, "Yandex", "https://yandex.ru"))
                Link(Discipline.Link(1, "Rambler", "https://rambler.ru"))
            }
        }

        Text(
            "Также как разбавленное изрядной долей эмпатии, рациональное мышление прекрасно подходит для реализации поставленных обществом задач.\n" +
                "\n" +
                "Предварительные выводы неутешительны: понимание сути ресурсосберегающих технологий не оставляет шанса для модели развития.\n" +
                "\n" +
                "Предварительные выводы неутешительны: семантический разбор внешних противодействий создаёт предпосылки для направлений прогрессивного развития.",
            style = AppTypography.bodyMedium,
        )

        TaskProgress(status = TaskProgress.Status.Completed, onChangeRequest = {})

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SuggestedTransitions(TaskProgress.Status.InProgress)
        }
    }
}
