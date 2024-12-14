package ru.ruscalworld.studyplanner.screens.editor.curriculum

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.DisciplineCard
import ru.ruscalworld.studyplanner.common.InputGroup
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.common.TextFieldRow
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.elements.field.Field

@Composable
fun CurriculumEditorScreen(
    viewModel: CurriculumEditorViewModel = hiltViewModel(),
    navigateToDiscipline: (Long) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val name by viewModel.name.collectAsState()
    val semesterNo by viewModel.semesterNo.collectAsState()

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.editor_curriculum_loading_title) },
            description = { stringResource(R.string.editor_curriculum_loading_description) },
        )
    }

    CommonLayout {
        Headline(
            title = { stringResource(R.string.editor_curriculum_title).toUpperCase(Locale.current) },
            highlight = true,
        )

        InputGroup {
            Field(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = { stringResource(R.string.editor_curriculum_name_label) },
            )

            TextFieldRow(
                labelWeight = 2f,
                label = { stringResource(R.string.editor_curriculum_semester_no_label) },
            ) {
                Field(
                    modifier = Modifier.fillMaxWidth(),
                    value = semesterNo,
                    onValueChange = { viewModel.onSemesterNoChanged(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
        }

        NamedCardContainer(
            title = { stringResource(R.string.editor_curriculum_disciplines_title) },
        ) {
            DisciplineCard(
                discipline = Discipline(1, "Sample discipline"),
                navigateTo = navigateToDiscipline,
            )
        }
    }
}
