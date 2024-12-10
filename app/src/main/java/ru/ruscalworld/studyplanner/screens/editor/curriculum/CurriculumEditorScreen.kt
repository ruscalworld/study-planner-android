package ru.ruscalworld.studyplanner.screens.editor.curriculum

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.LoadingScreen

@Composable
fun CurriculumEditorScreen(
    viewModel: CurriculumEditorViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.editor_curriculum_loading_title) },
            description = { stringResource(R.string.editor_curriculum_loading_description) },
        )
    }
}
