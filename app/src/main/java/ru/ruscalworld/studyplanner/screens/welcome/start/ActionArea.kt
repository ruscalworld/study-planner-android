package ru.ruscalworld.studyplanner.screens.welcome.start

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.ui.elements.button.Button

@Composable
fun ActionArea(isLoading: Boolean, authenticate: () -> Unit) {
    Button(isLoading = isLoading, onClick = authenticate) {
        stringResource(R.string.start_welcome_authenticate)
    }
}
