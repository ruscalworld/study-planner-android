package ru.ruscalworld.studyplanner.screens.editor.curriculum

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.DisciplineCard
import ru.ruscalworld.studyplanner.common.CommonLayout
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.common.InputGroup
import ru.ruscalworld.studyplanner.common.LoadingScreen
import ru.ruscalworld.studyplanner.common.TextFieldRow
import ru.ruscalworld.studyplanner.forms.discipline.create.CreateDisciplineModal
import ru.ruscalworld.studyplanner.ui.elements.card.CardButton
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer
import ru.ruscalworld.studyplanner.ui.elements.common.Headline
import ru.ruscalworld.studyplanner.ui.elements.field.Field
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun CurriculumEditorScreen(
    viewModel: CurriculumEditorViewModel = hiltViewModel(),
    navigateToDiscipline: (Long) -> Unit,
    scaffoldPadding: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()
    val name by viewModel.name.collectAsState()
    val semesterNo by viewModel.semesterNo.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var disciplineModalOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.editor_discipline_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    if (state.isLoading) {
        LoadingScreen(
            title = { stringResource(R.string.editor_curriculum_loading_title) },
            description = { stringResource(R.string.editor_curriculum_loading_description) },
        )

        return
    }

    CommonLayout(scaffoldPadding = scaffoldPadding) {
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
            state.disciplines?.let {
                for (discipline in it) {
                    DisciplineCard(discipline = discipline, navigateTo = navigateToDiscipline)
                }
            }

            CardButton(
                onClick = { disciplineModalOpen = true },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.fa_plus_solid),
                        tint = PrimaryText,
                        contentDescription = null,
                    )
                },
            ) {
                stringResource(R.string.editor_curriculum_disciplines_create)
            }
        }
    }

    state.curriculum?.let {
        CreateDisciplineModal(
            curriculumId = it.id,
            snackbarHostState = snackbarHostState,
            modalOpen = disciplineModalOpen,
            onDisciplineCreated = { discipline ->
                viewModel.onDisciplineAdded(discipline)
                disciplineModalOpen = false
            },
        )
    }

    SnackbarHost(hostState = snackbarHostState)
}
