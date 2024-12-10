package ru.ruscalworld.studyplanner.screens.options.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.ui.elements.card.CardButton
import ru.ruscalworld.studyplanner.ui.elements.card.NamedCardContainer
import ru.ruscalworld.studyplanner.ui.theme.AppTypography
import ru.ruscalworld.studyplanner.ui.theme.Background
import ru.ruscalworld.studyplanner.ui.theme.PrimaryBackground
import ru.ruscalworld.studyplanner.ui.theme.PrimaryText

@Composable
fun InfoScreen(
    viewModel: InfoViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        Header()
        Options()
    }
}

@Composable
fun Header() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(PrimaryBackground, Background)))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 64.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.options_info_logo_description),
                modifier = Modifier.height(96.dp),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    stringResource(R.string.options_info_app_name),
                    style = AppTypography.titleLarge,
                )

                Text(
                    stringResource(R.string.options_info_app_description),
                    style = AppTypography.labelMedium,
                )
            }
        }
    }
}

@Composable
fun Options() {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .padding(top = 32.dp)
            .safeGesturesPadding(),
    ) {
        NamedCardContainer(title = { stringResource(R.string.options_info_curriculums_title) }) {
            CardButton(
                onClick = {},
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
                onClick = {},
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
