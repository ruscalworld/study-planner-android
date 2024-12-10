package ru.ruscalworld.studyplanner.common

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import ru.ruscalworld.studyplanner.ui.exceptions.VisibleException

@Composable
fun ExceptionHandler(
    throwable: Throwable?,
    unknownErrorMessage: Int,
    snackbarHostState: SnackbarHostState? = null,
) {
    val usedSnackbarHostState = remember { snackbarHostState ?: SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(throwable) {
        throwable?.let {
            when (it) {
                is VisibleException -> usedSnackbarHostState.showSnackbar(context.getString(it.displayedMessage))
                else -> usedSnackbarHostState.showSnackbar(context.getString(unknownErrorMessage))
            }
        }
    }

    if (snackbarHostState == null) {
        SnackbarHost(hostState = usedSnackbarHostState)
    }
}
