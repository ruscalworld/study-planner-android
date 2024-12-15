package ru.ruscalworld.studyplanner.ui.elements.field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ruscalworld.studyplanner.ui.theme.SecondaryText
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    initialValue: Long?,
    modifier: Modifier = Modifier,
    onDatePicked: (Long?) -> Unit,
    confirmText: Int,
    dismissText: Int,
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialValue)
    var showModal by remember { mutableStateOf(false) }

    Surface(
        onClick = { showModal = true },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, SecondaryText),
        color = Color.Transparent,
        modifier = modifier,
    ) {
        Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 14.dp)) {
            val selectedDate = datePickerState.selectedDateMillis

            Text(
                if (selectedDate != null)
                    DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.SHORT)
                        .format(Instant.ofEpochMilli(selectedDate).atZone(ZoneId.systemDefault()).toLocalDate())
                else ""
            )
        }
    }

    if (showModal) {
        DatePickerDialog(
            confirmButton = {
                TextButton(
                    onClick = {
                        onDatePicked(datePickerState.selectedDateMillis)
                        showModal = false
                    }
                ) {
                    Text(stringResource(confirmText))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showModal = false
                    }
                ) {
                    Text(stringResource(dismissText))
                }
            },
            onDismissRequest = {
                showModal = false
            }
        ) {
            DatePicker(
                state = datePickerState,
            )
        }
    }
}
