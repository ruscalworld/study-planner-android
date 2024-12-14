package ru.ruscalworld.studyplanner.screens.options.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.adapters.CurriculumCard
import ru.ruscalworld.studyplanner.ui.elements.card.CardButton
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun Options(
    viewModel: InfoViewModel = hiltViewModel(),
    navigateToPickCurriculum: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .padding(top = 32.dp)
            .safeGesturesPadding(),
    ) {
        NamedCardContainer(title = { stringResource(R.string.options_info_curriculums_title) }) {
            state.curriculums?.let {
                for (curriculum in it) {
                    CurriculumCard(
                        curriculum = curriculum,
                        onClick = { viewModel.pickCurriculum(curriculum) }
                    )
                }
            }

            CardButton(
                onClick = { navigateToPickCurriculum() },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.fa_plus_solid),
                        contentDescription = stringResource(R.string.options_info_curriculums_add),
                        tint = PrimaryText,
                    )
                }
            ) {
                stringResource(R.string.options_info_curriculums_add)
            }
        }

        NamedCardContainer(title = { stringResource(R.string.options_info_other_title) }) {
            CardButton(
                onClick = { viewModel.signOut() },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.fa_right_from_bracket_solid),
                        contentDescription = stringResource(R.string.options_info_other_sign_out),
                        tint = PrimaryText,
                    )
                }
            ) {
                stringResource(R.string.options_info_other_sign_out)
            }
        }
    }
}
