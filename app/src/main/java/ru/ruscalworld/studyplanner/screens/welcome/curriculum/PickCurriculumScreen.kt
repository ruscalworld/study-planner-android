package ru.ruscalworld.studyplanner.screens.welcome.curriculum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.common.ExceptionHandler
import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.forms.curriculum.create.CreateCurriculumModal
import ru.ruscalworld.studyplanner.ui.elements.button.Button
import ru.ruscalworld.studyplanner.ui.theme.AppTypography


@Composable
fun PickCurriculumScreen(
    viewModel: PickCurriculumViewModel = hiltViewModel(),
    navigateToCurriculum: (Long) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarHost(hostState = snackbarHostState)

    LaunchedEffect(state.curriculum) {
        state.curriculum?.let { navigateToCurriculum(it.id) }
    }

    ExceptionHandler(
        throwable = state.error,
        unknownErrorMessage = R.string.start_curriculum_add_existing_unknown_error,
        snackbarHostState = snackbarHostState,
    )

    Column(
        modifier = Modifier
            .safeGesturesPadding()
            .fillMaxWidth()
            .fillMaxHeight()
            .imePadding(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        ActionArea(
            isLoading = state.isLoading,
            snackbarHostState = snackbarHostState,
            onCurriculumCreated = { navigateToCurriculum(it.id) },
            onAddExistingRequest = { viewModel.addExistingCurriculum(it) }
        )
    }
}

@Composable
fun Header() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("animations/magnification_glass.json"))
    val compact = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    Column(
        modifier = Modifier.padding(vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(64.dp)
    ) {
        if (!compact) {
            LottieAnimation(composition, modifier = Modifier.height(128.dp))
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                stringResource(R.string.start_curriculum_title),
                style = AppTypography.headlineMedium,
                textAlign = TextAlign.Center,
            )

            Text(
                stringResource(R.string.start_curriculum_text),
                style = AppTypography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ActionArea(
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    onCurriculumCreated: (Curriculum) -> Unit,
    onAddExistingRequest: (AddExistingCurriculumRequest) -> Unit,
) {
    var modalOpen by remember { mutableStateOf(false) }
    val inviteCode by remember { mutableStateOf(TextFieldValue()) }

    CreateCurriculumModal(
        modalOpen = modalOpen,
        onClosed = { modalOpen = false },
        onCurriculumCreated = onCurriculumCreated,
        snackbarHostState = snackbarHostState,
    )

    Column(
        modifier = Modifier.width(256.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (inviteCode.text.isBlank()) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { modalOpen = true },
                isLoading = isLoading,
            ) {
                stringResource(R.string.start_curriculum_create_new)
            }
        } else {
            Button(
                modifier = Modifier.fillMaxWidth(),
                isLoading = isLoading,
                onClick = {
                    onAddExistingRequest(AddExistingCurriculumRequest(inviteCode.text))
                },
            ) {
                stringResource(R.string.start_curriculum_add_existing)
            }
        }
    }
}

data class AddExistingCurriculumRequest(
    val code: String,
)
