package ru.ruscalworld.studyplanner.ui.elements.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.ui.elements.button.Button
import ru.ruscalworld.studyplanner.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetForm(
    isVisible: Boolean,
    isLoading: Boolean = false,
    onClosed: () -> Unit,
    confirmText: @Composable () -> String,
    titleText: @Composable () -> String,
    onConfirm: () -> Unit,
    content: @Composable (focusRequester: FocusRequester) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isVisible) {
        scope.launch {
            if (isVisible) {
                sheetState.show()
            } else {
                sheetState.hide()
            }
        }
    }

    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onClosed,
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier.padding(bottom = 32.dp, top = 0.dp, start = 32.dp, end = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(titleText(), style = AppTypography.headlineMedium)

                content(focusRequester)

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    isLoading = isLoading,
                    onClick = onConfirm,
                ) {
                    confirmText()
                }
            }
        }
    }
}
